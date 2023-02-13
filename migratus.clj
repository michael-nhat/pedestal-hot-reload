

{:store :database
 :migration-dir "migrations"
 :db
 ;; "jdbc:postgresql://postgres:postgres@localhost:5432/mig_clj"
 {:dbtype "postgres"
  :dbname (nth (.split (get (System/getenv) "BE1") ",") 0)
  :user (nth (.split (get (System/getenv) "BE1") ",") 1)
  :password (nth (.split (get (System/getenv) "BE1") ",") 2)}}
