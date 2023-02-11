 /usr/local/bin/clojure -Sdeps '{:deps {nrepl/nrepl {:mvn/version "1.0.0"} cider/cider-nrepl {:mvn/version "0.29.0"}} :aliases {:cider/nrepl {:main-opts ["-m" "nrepl.cmdline" "--middleware" "[cider.nrepl/cider-middleware]"]}}}' -M:cider/nrepl

fetch("http://localhost:8081/test", {
        mode: "cors" // <----------------
    })
    .then((res)=>{
        return res.text();
    })
    .then((data)=>{
        console.log(data);
        return new Promise((resolve, reject)=>{
            resolve(data ? JSON.parse(data) : {})
        })
    })

#### todo:
    - [v] create db, connect db
    - [v] migration lib, create schema with toucan
    - [v] json return
    - [v] with error handling
    - [v] with ring
    - [] db migrate (migratus ?)
    - [] login
    - [] get users, list user
    - [] todo

stupid
https://stackoverflow.com/questions/32322110/compojure-ring-json-not-returning-json

https://coderanch.com/t/667352/languages/Unable-find-read-form-parameters

https://github.com/clojure-emacs/cider/issues/3232

https://javahippie.net/clojure/cloud/2020/04/21/clojurenative02.html

;; login
;; signup

;; change password

;; design restapi: logic should be in api, so bad request don't make
;; and it could
;; inconsistion data, when a safe logic, it could be check at frontend
;; fast grown also be prior
