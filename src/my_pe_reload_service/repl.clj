(ns my-pe-reload-service.repl
  (:refer-clojure :exclude [send])
  (:require [cider.nrepl :refer [cider-nrepl-handler]]
            [nrepl.core :as nrepl]
            [nrepl.server :as nrepl-server]
            [nrepl.transport :as transport]
            [nrepl.cmdline]
            [my-pe-reload-service.server :as server] ;; [nrepl.transport :refer [send]]
            [rebel-readline.main :as rebel]
            [nrepl.cmdline :as cmdline]))
;; (pedestal-hello1.pedestal-hello1/-main)

(def conn)

(defn greeting-fn [t]
  (print "wtf, gretting")
  ;; (ns pedestal-hello1.pedestal-hello1)
  (transport/send t {:op "eval" :code "(ns 'pedestal-hello1.pedestal-hello1)"})
  (transport/send t {:op "eval" :code "(def conn 3)" :ns "pedestal-hello1.pedestal-hello1"})
  (transport/send t {:out (str ";; nREPL " "wtf anh nhat oi")})
  ;; (nrepl/message t {:out "(+ 2 3)"})
  ;; don't work with code
  ;; (require 'pedestal-hello1.pedestal-hello1)
  )

(defn -main [& _args]
  ;; (println "wtf: ")
  ;; (println _args)
  ;; (println "wtf end: ")

;;   (nrepl-server/start-server :port 7889 :handler cider-nrepl-handler)
  ;; (nrepl.cmdline/-main :port 7885 :handler cider-nrepl-handler :greeting-fn greeting-fn)
  (server/-main [])
  (ns pedestal-hello1.pedestal-hello1)
  ;; main has to run first
  (apply nrepl.cmdline/-main _args)
  (println "my nrepl server ")
  (rebel/-main)
  (System/exit 0))
