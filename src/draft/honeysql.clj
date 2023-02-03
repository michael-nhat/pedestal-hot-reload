(ns user
  (:require [toucan.db :refer [] :as touc]
            [hugsql.core :as hug]
            [toucan.db :as db]
            [toucan.models :as model]
            [next.jdbc :as jdbc]))

(ns-unalias *ns* 'j)

(db/set-default-db-connection!
 {:classname "org.postgresql.Driver"
  :subprotocol "postgresql"
  :subname "//localhost:5432/shop_udemy"
  :user "postgres"
  :password "postgres"})


(def pg-db {:dbtype "postgresql"
            :port 5432
            :user "postgres"
            :dbname "test_clojure"
            :password "postgres"})

(jdbc/query pg-db ["SELECT current_database()"])
(jdbc/execute! )
j/
jdbc/

(defn j-do-commands [command]
  (jdbc/db-do-commands pg-db true [command]))

(jdbc/db-do-commands pg-db false "CREATE DATABASE test_clojure")
(jdbc/db-do-commands pg-db true "SELECT current_database()")
(jdbc/db-do-commands pg-db false "SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;")
(jdbc/execute! pg-db "select 3;")

(j-do-commands "select CURRENT_DATE;")


(jdbc/create-table-ddl "student" nil)
(jdbc/query pg-db "select CURRENT_DATE;")
(jdbc/query pg-db ["select ??;" 3 3])

(model/defmodel Book :book)

(db/select- ['Database :name] :id )

(Book 1)

(System/setProperty "org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog")
(System/setProperty "org.eclipse.jetty.LEVEL", "OFF")

(.getLogger
 org.eclipse.jetty.util.log.Logger
 )


