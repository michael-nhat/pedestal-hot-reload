(ns my-pe-reload-service.ring-server
  (:require [compojure.core :as comp :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.util.response :as r-util]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.refresh :refer [wrap-refresh]]
            [ring.middleware.json :as r-json]
            [ring.middleware.params :refer [params-request wrap-params]]
            [ring.middleware.keyword-params :as r-kw]
            [ring.middleware.defaults :as r-default]
            [ring.middleware.cors :as r-cors]))

(defroutes routes
  (GET "/" [] "<h1>Hello 3332kworldkkk</h1>")
  (GET "/user/:id/:greeting" [id greeting] (str "<h1> greet" greeting " user " id "</h1>"))
  (GET "/arg2" [] (fn [& args] "<h1>xxlksjdkljdsflfound</h1>"))
  (POST "/test-post" name
    (prn "hello " name " !"))
  ;; if no "real" response, it would run  to not found
  (POST "/aa" name
    (r-util/response (prn name)))
  (GET "/ww" request (r-util/response (prn request)))
  (comp/GET "/test-post" []
    (str "hello " 8 " !"))
  (GET "/test" [] {:status 200
                   :headers {"Content-Type" "application/json"}
                   :body {:wtf "dsj"}})
  (GET "/arg" [] (fn [& args]
                   (constantly {:status 200 :headers {"Content-Type" "text/html"}
                                :body (pr-str args)})))
  (route/not-found "<h1>Not fklkjalfdskound</h1>"))

(def app (-> routes
             ;; (r-cors/wrap-cors :access-control-allow-origin [#".*"]
             ;;                   :access-control-allow-methods [:get :put :post :delete])
             (r-json/wrap-json-body {:keywords? true, :bigdecimals? true})
             r-json/wrap-json-response
             r-json/wrap-json-params
             r-kw/wrap-keyword-params
             ;; wrap-reload
             (r-default/wrap-defaults
              ;; r-default/site-defaults
              ;; (assoc-in r-default/site-defaults [:security :anti-forgery] false)
              r-default/api-defaults
                                      )))

(defn -main
  []
  (run-jetty #'app {:port 8081 :join? false}))
