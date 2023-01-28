(ns my-pe-reload-service.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [ring.middleware.json :as r-json]
            [clojure.data.json :as json]
            [io.pedestal.http.content-negotiation :as conneg]
            [my-pe-reload-service.hipedestal :as hi]))
(def random-thing (rand))

(defn about-page [request]
  (ring-resp/response (format "Clojure %s - served from %s wtf wtf%s "
                              (clojure-version)
                              random-thing
                              (route/url-for ::about-page))))
(defn home-page [request]
  (ring-resp/response "fsdfsffd dfsdfd sfsdsdfsddddddddsaaaaddd xxxxx"))

(defn handler [request]
  (ring-resp/response {:foo "bar"}))

(defn test-json [req]
  (r-json/wrap-json-response handler))

;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])
(def json-interceptors [r-json/wrap-json-response])

;; Tabular routes
(defn routes [] #{["/" :get (conj common-interceptors `home-page)]
                  ["/about" :get (conj common-interceptors `about-page)]
                  ;; ["/wtf" :get 'test-json]
                  ["/greet" :get [hi/coerce-body hi/content-neg-intc hi/respond-hello] :route-name :greet]
                  ["/echo" :get hi/echo]
                  })

;; Map-based routes
;(def routes `{"/" {:interceptors [(body-params/body-params) http/html-body]
;                   :get home-page
;                   "/about" {:get about-page}}})

;; Terse/Vector-based routes
;(def routes
;  `[[["/" {:get home-page}
;      ^:interceptors [(body-params/body-params) http/html-body]
;      ["/about" {:get about-page}]]]])


;; Consumed by my-pe-reload-service.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; ::http/interceptors []
              ::http/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::http/allowed-origins ["scheme://host:port"]

              ;; Tune the Secure Headers
              ;; and specifically the Content Security Policy appropriate to your service/application
              ;; For more information, see: https://content-security-policy.com/
              ;;   See also: https://github.com/pedestal/pedestal/issues/499
              ;;::http/secure-headers {:content-security-policy-settings {:object-src "'none'"
              ;;                                                          :script-src "'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:"
              ;;                                                          :frame-ancestors "'none'"}}

              ;; Root for resource interceptor that is available by default.
              ::http/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ;;  This can also be your own chain provider/server-fn -- http://pedestal.io/reference/architecture-overview#_chain_provider
              ::http/type :jetty
              ;;::http/host "localhost"
              ::http/port 8081
              ;; Options to pass to the container (Jetty)
              ::http/container-options {:h2c? true
                                        :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                        :ssl? false
                                        ;; Alternatively, You can specify your own Jetty HTTPConfiguration
                                        ;; via the `:io.pedestal.http.jetty/http-configuration` container option.
                                        ;:io.pedestal.http.jetty/http-configuration (org.eclipse.jetty.server.HttpConfiguration.)
                                        }})
