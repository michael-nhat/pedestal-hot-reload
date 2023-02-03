 /usr/local/bin/clojure -Sdeps '{:deps {nrepl/nrepl {:mvn/version "1.0.0"} cider/cider-nrepl {:mvn/version "0.29.0"}} :aliases {:cider/nrepl {:main-opts ["-m" "nrepl.cmdline" "--middleware" "[cider.nrepl/cider-middleware]"]}}}' -M:cider/nrepl

#### todo:
    - [v] create db, connect db
    - [v] migration lib, create schema with toucan
    - [v] json return
    - [v] with error handling
    - [v] with ring
    - [] db migrate (migratus ?)
    - [] get users, list user
    - [] login
    - [] todo

stupid
https://stackoverflow.com/questions/32322110/compojure-ring-json-not-returning-json
https://coderanch.com/t/667352/languages/Unable-find-read-form-parameters
