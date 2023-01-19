(ns user
  (:require [toucan.db :refer [] :as touc]
            [hugsql.core :as hug]
            [toucan.db :as db]
            [toucan.models :as model]
            [clojure.java.jdbc :as j]))

(db/set-default-db-connection!
 {:classname "org.postgresql.Driver"
  :subprotocol "postgresql"
  :subname "//localhost:5432/shop_udemy"
  :user "postgres"
  :password "postgres"})


(def pg-db {:dbtype "postgresql"
            :port 5432
            :user "postgres"
            :dbname "shop_udemy"
            :password "postgres"})


(j/query pg-db ["SELECT current_database()"])



(model/defmodel Book :book)

(db/select- ['Database :name] :id )

(Book 1)

