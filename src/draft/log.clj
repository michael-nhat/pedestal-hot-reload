(ns draft.log)

;; (timbre/debug "hello wtf2")
  ; will print 
(def example-config  {:level :warn})
(timbre/merge-config! example-config)
  ; update the configuration 
(timbre/debug "hello wtf1")
;; (System/setProperty "org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog")
;; (System/setProperty "org.eclipse.jetty.LEVEL", "WARN")
