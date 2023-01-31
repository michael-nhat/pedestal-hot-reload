(ns my-pe-reload-service.ring-watch
  (:require [com.brunobonacci.mulog :as u]
            [mount.core :refer [defstate]]
            [mount.core :as mount]
            [muuntaja.core :as m] ;;  [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.middleware :refer [wrap-format]]
            [reitit.ring :as ring]
            [ring.adapter.jetty :as ring-jetty]
            [ring.middleware.refresh :refer [wrap-refresh]]
            [ring.middleware.resource :refer [wrap-resource]]
            [xtdb.api :as xt]
            ;; so 
            [eplme.backend.loggo :refer [mulog]]
            [eplme.dev.test-common :refer [node]]))
(def m (m/create))

(def edn-fmt
  {:Content-type "application/edn"})

(defn index []
  (slurp "resources/public/index.html"))

(defn handler [x]
  (println "HANDLE1" x)
  (spit "output/handle.txt" x)
  x)

(defn wrap-tracking-events
  "tracks api events with μ/log."
  [handler]
  (fn [req]
    ;; enhance the context with the info that
    ;; will be attached to all events within
    ;; this request.
    (u/with-context
      {:uri            (get req :uri)
       :request-method (get req :request-method)}

      ;; track the request duration and outcome
      (u/trace ::http-request
        ;; add here all the key/value pairs for tracking event only
               {:pairs [:content-type     (get-in req [:headers "content-type"])
                        :content-encoding (get-in req [:headers "content-encoding"])]
         ;; out of the response capture the http status code.
                :capture (fn [{:keys [status]}] {:http-status status})}
        ;; call the request handler
               (handler req)))))

(defn respond-hello [request]
  {:status 200 :body "Hello, nurds!!"
   :Content-type "text/plain"})
(defn respond-edn [request]
  {:status 200 :body {:thing "yESSS"
                      :some '(1 2 3)
                      :thingy [:a 1 6]
                      :when {:x :y}}})

(defn respond-classes [request]
  (u/trace ::serve-classes []
           {:status 200 :body
            (into [] (flatten (seq (xt/q (xt/db node)
                                         '{:find [cls?]
                                           :where [[:rhoplm/meta :classes cls?]]}))))}))

(defn respond-body [request]
  (u/trace ::serve-index
           []
           {:status 200 :body (index)}))
(def dd (atom nil))
(comment
  @dd
  (:body-params @dd))
(def app
  (-> (ring/ring-handler
       (ring/router
        ["/"
         ["" {:handler respond-body}]
         ["api/"
          ["greet/" {:handler respond-hello}]
          ["edndemo/" {:handler respond-edn}]
          ["class/" {:get respond-classes
                     :put (fn [q]
                            (reset! dd q)
                            {:status 204})}]
          ["demo/" {:handler respond-hello}
           ["maps/" {:get (fn [request]
                            {:status 200
                             :body {:png "/renders/TEST/g.png"
                                    :map "/renders/TEST/g.map"
                                    :cmap "/renders/TEST/g.cmap"}})}]]]]))
      (wrap-resource "public")
      (wrap-refresh)
      (wrap-format)
      (wrap-tracking-events)))

(defstate service
  :start (ring-jetty/run-jetty #'app {:port 8080
                                      :join? false})
  :stop (.stop service))

(comment
  (app {:request-method :get :uri "/api/greet/"})
  (app {:request-method :get :uri "/"})
  (app {:request-method :get :uri "/api/demo/maps/"})
  (mulog)
  (mount/start)
  (mount/stop))
