(ns user
  (:require [toucan.db :refer [] :as touc]
            [hugsql.core :as hugsql]
            ;; [hugsql. :as hug]
            [toucan.db :as db]
            [toucan.models :as model]
            [next.jdbc :as jdbc]
            [hugsql.adapter.clojure-java-jdbc
             :as adapter]))


(def db {:dbtype "postgres" :dbname "test_clojure" :user "postgres"
         :password "postgres" :port 5432})

(def ds (jdbc/get-datasource db))

(jdbc/execute! ds ["
create table address (
  id int primary key,
  name varchar(32),
  email varchar(255)
)"])

(jdbc/execute! ds ["select 3 as a, 4 as b, 5 as c; "])

(hugsql/def-db-fns (clojure.java.io/file "db/example.sql")
  {:adapter (adapter/hugsql-adapter-clojure-java-jdbc)})

(-> (clojure.java.io/file "db/example.sql") .getAbsolutePath)

(slurp "deps.edn")

(clojure.java.io/reader "db/example.sql")

(jdbc/execute! ds ["create database mig_clj"])

(jdbc/execute! ds ["set search_path = mig_clj;"])
(jdbc/execute! ds ["select current_database()"])
(jdbc/execute! ds ["SELECT session_user, current_database();"])

