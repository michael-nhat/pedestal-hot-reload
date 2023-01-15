(ns user
  (:require [toucan.db :refer [] :as touc]
            [hugsql.core :as hug]
            [toucan.db :as db]
            [toucan.models :as model]
            []))

(db/set-default-db-connection!
 {:classname "org.postgresql.Driver"
  :subprotocol "postgresql"
  :subname "//localhost:5432/toucan_db"
  :user "cam"})

(model/defmodel User :user_table)

(db/select-one-field :first-name User)

