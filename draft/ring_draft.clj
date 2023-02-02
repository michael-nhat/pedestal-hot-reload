(ns my-pe-reload-service.ring-draft
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.refresh :refer [wrap-refresh]]
            ;; [my-pe-reload-service.watch-middleware-ring :refer [wrap-refresh]]
            ))

(defn- create-test-handler [body]
  (-> (constantly {:status 200
                   :headers {"Content-Type" "text/html"}
                   :body "Content-Type"})
      wrap-refresh))

(defroutes app
  (GET "/" [] "<h1>Hello 3332kworldkkk</h1>")
  (GET "/arg3" [] {:status 200
                   :headers {"Content-Type" "text/json"}
                   :body "<h1>Hello user 1</h1>"})
  (GET "/arg2" [] (fn [& args] "<h1>xxlksjdkljdsflfound</h1>"))
  (GET "/test" [] (constantly {:status 200
                               :headers {"Content-Type" "text/html"}
                               :body "<h1>test ahikjl </h1>"}))
  ;; (GET "/tt" [] (create-test-handler "<h1> lsjdflssdkkkfs</h1>"))
  (GET "/arg" [] (fn [& args]
                   (constantly {:status 200 :headers {"Content-Type" "text/html"}
                                :body (pr-str args)})))
  (route/not-found "<h1>Not fklkjalfdskound</h1>"))

;; (def app2 (wrap-refresh app))

;; (def app3 (wrap-refresh app))

;; (wrap-refresh)
(defn -main
  []
  (run-jetty app3 {:port 8081 :join? false}))

;; (defn -main []
;;   (print "ok"))
