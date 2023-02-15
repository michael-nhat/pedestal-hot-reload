(ns app.migrations.todo-table)
(defn migrate-up [config]
   ;; do stuff here
  (print "mig up todo -table"))

;; could seed here

(defn migrate-down [config]
   ;; maybe undo stuff here
  (print "mig down todo -table"))
