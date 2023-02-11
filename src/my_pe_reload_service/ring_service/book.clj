(ns my-pe-reload-service.ring-service.book (:require
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

(defn create [book, user])

(defn update [book, user])

(defn delete [])

(defn find-one [])

(defn find [])
