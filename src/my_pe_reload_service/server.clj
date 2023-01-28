(ns my-pe-reload-service.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [my-pe-reload-service.service :as service]
            [ns-tracker.core :refer [ns-tracker]]))

;; This is an adapted service map, that can be started and stopped
;; From the REPL you can call server/start and server/stop on this service
;; (defonce runnable-service (server/create-server service/service))

(defonce modified-namespaces
  (ns-tracker ["src"]))
(def wtf 343343)

(defn watch-routes-fn [routes]
    (doseq [ns-sym (modified-namespaces)]
      (require ns-sym :reload))
    (println "wtf route:d" routes)
    routes)

(defn -main
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> service/service ;; start with production configuration
      (merge {:env :dev
              ;; do not block thread that starts web server
              ::server/join? false
              ;; Routes can be a function that resolve routes,
              ;;  we can use this to set the routes to be reloadable
              ;; ::server/routes service/routes
              ;; ::server/routes #(route/expand-routes (service/routes))
              ::server/routes #(route/expand-routes (watch-routes-fn  (service/routes)))
              ;; all origins are allowed in dev mode
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}
              ;; Content Security Policy (CSP) is mostly turned off in dev mode
              ::server/secure-headers {:content-security-policy-settings {:object-src "'none'"}}})
      ;; Wire up interceptor chains
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start)
  (println "wtfwtf"))

;; (defn -main2
;;   "The entry-point for 'lein run'"
;;   [& args]
;;   (println "\nCreating your server...")
;;   (server/start runnable-service))

;; If you package the service up as a WAR,
;; some form of the following function sections is required (for io.pedestal.servlet.ClojureVarServlet).

;;(defonce servlet  (atom nil))
;;
;;(defn servlet-init
;;  [_ config]
;;  ;; Initialize your app here.
;;  (reset! servlet  (server/servlet-init service/service nil)))
;;
;;(defn servlet-service
;;  [_ request response]
;;  (server/servlet-service @servlet request response))
;;
;;(defn servlet-destroy
;;  [_]
;;  (server/servlet-destroy @servlet)
;;  (reset! servlet nil))
