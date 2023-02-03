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
            [taoensso.timbre :as timbre ]
            ))

(timbre/debug "hello wtf2")
  ; will print 
(def example-config  {:level :warn})
(timbre/merge-config! example-config)
  ; update the configuration 
(timbre/debug "hello wtf1")
;; (System/setProperty "org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog")
;; (System/setProperty "org.eclipse.jetty.LEVEL", "WARN")

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
                          :headers {"Content-Type" "application/json"}
                          :body {:wtf "dsj"}}))
  (GET "/arg" [] (fn [& args]
                   (constantly {:status 200 :headers {"Content-Type" "text/html"}
                                :body (pr-str args)})))
  (route/not-found "<h3>Something wrong ?!</h3>"))

(def app (-> routes
             ;; (r-cors/wrap-cors :access-control-allow-origin [#".*"]
             ;;                   :access-control-allow-methods
             ;;                   [:get :put :post :delete :patch])
             wrap-params
             (r-json/wrap-json-body {:keywords? true, :bigdecimals? true})
             r-json/wrap-json-response
             (r-default/wrap-defaults r-default/api-defaults)
             r-json/wrap-json-params
             r-kw/wrap-keyword-params))

(def app-reload (wrap-reload app))

(defonce server (run-jetty #'app-reload {:port 8081 :join? false}))

(comment (.stop server))

(defn -main []
  (.start server))
