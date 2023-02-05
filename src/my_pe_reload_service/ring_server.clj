(ns my-pe-reload-service.ring-server
  (:require [compojure.core :as comp :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.util.response :as r-util :refer [response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.refresh :refer [wrap-refresh]]
            [ring.middleware.json :as r-json]
            [ring.middleware.params :as params :refer [params-request wrap-params]]
            [ring.middleware.keyword-params :as r-kw]
            [ring.middleware.defaults :as r-default]
            [ring.middleware.cors :as r-cors]
            ;; [ring.logger :as logger]
            [taoensso.timbre :as timbre]
            [jumblerg.middleware.cors :refer [wrap-cors]]))

(defroutes routes
  (GET "/user/:id/:greeting" [id greeting] (str "<h1> greet" greeting " user " id "</h1>"))
  (GET "/" [] "<h1>Hello 3332kworldkkk</h1>")
  (GET "/arg2" [] (fn [& args] "<h1>xxlksjdkljdsflfound</h1>"))
  (POST "/test-post" name
    (response (prn "hello " name " !")))
  ;; if no "real" response, it would run  to not found
  (comp/PATCH "/hello" {{x :name} :params}
    (response (str "Hello " x)))
  (POST "/aa" {{x :name} :params}
    (response (str "hello " x)))
  (POST "/ab" req
    (let [{x :params} req] (r-util/response (str x))))
  (POST "/req" req
    (do (print "reqww: ") (prn req) (response req)))
  (POST "/hello" req
    (let [{x :params} req] (r-util/response (str x))))
  (GET "/ww" request (str request))
  (comp/GET "/test-post" []
    (str "hello " 8 " !"))
  (GET "/test" [] (do (print 'wtf) {:status 200
                                    ;; :headers {"Content-Type" "application/json"
                                    ;;           "Access-Control-Allow-Origin" "*"}
                                    :body {:wtf "dsj"}}))
  (GET "/arg" [] (fn [& args]
                   (constantly {:status 200 :headers {"Content-Type" "text/html"}
                                :body (pr-str args)})))
  (route/not-found "<h3>Something wrong ?!</h3>"))

(defn allow-cross-origin
  "Middleware function to allow cross origin requests from browsers.
  When a browser attempts to call an API from a different domain, it makes an OPTIONS request first to see the server's
  cross origin policy.  So, in this method we return that when an OPTIONs request is made.
  Additionally, for non OPTIONS requests, we need to just returm the 'Access-Control-Allow-Origin' header or else
  the browser won't read the data properly.
  The above notes are all based on how Chrome works. "
  ([handler]
   (allow-cross-origin handler "http://localhost:8080"))
  ([handler allowed-origins]
   (fn [request]
     (-> (handler request)                     ; Don't pass the requests down, just return what the browser needs to continue.
         (assoc-in [:headers "Access-Control-Allow-Origin"] allowed-origins)
         (assoc-in [:headers "Access-Control-Allow-Methods"] "*")
         (assoc-in [:headers "Access-Control-Allow-Credentials"] "true")

         (assoc-in [:headers "Access-Control-Allow-Headers"] "Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization")
         ;; (assoc-in [:headers "Access-Control-Allow-Headers"] "X-Requested-With,Content-Type,Cache-Control,Origin,Accept,Authorization")
         (assoc :status 200)))))

(def app (-> routes
             wrap-params
             ;; (wrap-cors #".*")
             ;; (wrap-cors identity)
             (r-json/wrap-json-body {:keywords? true, :bigdecimals? true})
             r-json/wrap-json-response
             (r-default/wrap-defaults r-default/api-defaults)
             r-json/wrap-json-params
             allow-cross-origin
             ;; (r-cors/wrap-cors :access-control-allow-origin
             ;;                   ;; #"[\s\S]+"
             ;;                   [#"http://localhost:8080"]
             ;;                   :access-control-allow-methods [:get :put :post :delete :patch]
             ;;                   :access-control-allow-headers #{"Content-Type"})
             r-kw/wrap-keyword-params
             ;; (r-cors/wrap-cors :access-control-allow-origin [#"http://localhost:8080"]
             ;;                   :access-control-allow-methods [:get :put :post :delete])
             ))

(def app-reload (wrap-reload app))

(defonce server (run-jetty #'app-reload {:port 8081 :join? false}))

(comment (.stop server))

(defn -main []
  (.start server))
