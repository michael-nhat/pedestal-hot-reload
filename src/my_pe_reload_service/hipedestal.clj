(ns my-pe-reload-service.hipedestal
 (:require
  [clojure.data.json :as json]
  [io.pedestal.http :as http]
  [io.pedestal.http.content-negotiation :as conneg]
  [io.pedestal.http.route :as route]))

(def unmentionables #{"YHWH" "Voldemort" "Mxyzptlk" "Rumplestiltskin" "曹操"})

(defn ok [body]
  ;; return response type
  {:status 200 :body {:data body} :headers {"Content-Type" "application/json"}})

(defn not-found []
  {:status 404 :body "Not found\n"})

(defn greeting-for [nm]
  (cond
    (unmentionables nm) nil
    (empty? nm)         "Hello, world!\n"
    :else               (str "Hello, " nm "\n")))

(defn respond-hello [request]
  (let [nm   (get-in request [:query-params :name])
        resp (greeting-for nm)]
    (if resp
      (ok resp)
      (not-found))))

(def echo
  {:name ::echo
   :enter #(assoc % :response (ok (:request %)))})

(def supported-types ["text/html" "application/edn" "application/json" "text/plain"])

(def content-neg-intc (conneg/negotiate-content supported-types))

(defn accepted-type
  [context]
  (get-in context [:request :accept :field] "text/plain"))

(defn transform-content
  [body content-type]
  (case content-type
    "text/html"        body
    "text/plain"       body
    "application/edn"  (pr-str body)
    "application/json" (json/write-str body)))

(defn coerce-to
  [response content-type]
  (-> response
      (update :body transform-content content-type)
      (assoc-in [:headers "Content-Type"] content-type)))

(def coerce-body
  {:name ::coerce-body
   :leave
   (fn [context]
     (print "wtf 2coerce:" context)
     (cond-> context
       (nil? (get-in context [:response :headers "Content-Type"]))                    ;; <1>
       (update-in [:response] coerce-to (accepted-type context))))})

(def routes
  (route/expand-routes
   #{["/greet" :get [coerce-body content-neg-intc respond-hello] :route-name :greet]
     ["/echo"  :get echo]}))

(def server (atom nil))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type   :jetty
    ::http/port   8081
    ::http/join?  false}))                                                            ;; <2>

(defn start []
  (swap! server
         (constantly (http/start (create-server))))                                   ;; <3>
  nil)

(defn stop []
  (swap! server http/stop)                                                            ;; <4>
  nil)
