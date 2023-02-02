 /usr/local/bin/clojure -Sdeps '{:deps {nrepl/nrepl {:mvn/version "1.0.0"} cider/cider-nrepl {:mvn/version "0.29.0"}} :aliases {:cider/nrepl {:main-opts ["-m" "nrepl.cmdline" "--middleware" "[cider.nrepl/cider-middleware]"]}}}' -M:cider/nrepl

#### todo:
    - v.create db, connect db
    - migration lib, create schema with toucan
    - pedestal api, crud user, login
    - json return with error handling
    -- with ring
