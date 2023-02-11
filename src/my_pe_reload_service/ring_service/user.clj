(ns my-pe-reload-service.ring-service.user
  (:require
   [compojure.core :as comp :refer [defroutes GET POST]]
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
   [taoensso.timbre :as timbre]
   [jumblerg.middleware.cors :refer [wrap-cors]]
   [ring.util.http-response :as rs]))

(defn login "login " [username pass]
  (if (and (= username "nhat") (= pass "123456"))
    (rs/ok {:data {:username "nhat",
                   :fullname "h v nhat"
                   :token "auth-token"}})
    (rs/not-found {:msg "wrong pass or username"})))

(defn register "check exist then response"
  [username pass fullname]
  (let [check-exist true
        check-username true
        check-pass true
        check-fullname true]
    (cond
      (not (or check-username check-pass check-pass check-fullname)) (rs/bad-request {:msg "bad register info!"})
      check-exist (rs/ok {:data {:username "nhat"
                                 :fullname "h v nhat"}})
      :else (rs/bad-request {:msg "account existed!"}))))

(defn retrieve-password []
  (rs/ok {:data true}))
