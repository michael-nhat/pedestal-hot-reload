(ns user2
  (:require [libpython-clj2.require :refer [require-python]]
            [libpython-clj2.python :refer [py. py.. py.-] :as py]
            [tech.v3.datatype :as dtype]))
(ns user2)


(require '[libpython-clj2.python
                 :refer [as-python as-jvm
                         ->python ->jvm
                         get-attr call-attr call-attr-kw
                         get-item initialize!
                         run-simple-string
                         add-module module-dict
                         import-module
                         python-type
                         dir]
           :as py])
(initialize!)

(run-simple-string "print('hey')")
(def main-globals (-> (add-module "__main__")
                            (module-dict)))

(get main-globals "__name__")

(.put main-globals "my_var" 200)

(run-simple-string "print('your variable is:' + str(my_var))")

(print main-globals)

(ns user3)

(require '[libpython-clj2.require :refer [require-python]])
(require '[libpython-clj2.python :refer [call-attr get-item get-attr py.]])
(require '[libpython-clj2.python :as py])
(require-python '[numpy :as np])
(require-python '[pandas :as pan])

(def dates (pan/date_range "1/1/2000" :periods 8))
(def table (pan/DataFrame (print (np/random :randn 8 4)) :index dates :columns ["A" "B" "C" "D"]))

(np/linspace 2 3 :num 10)
(py/py. np/random/randn 4)
(defmacro a1 [fun res] `(py. np/random ~fun ~res))
(macroexpand `(a1 "randn" 4))
(py. np/random "randn" 4)
(a1 "randn" 4)
(macroexpand `(a1 randn 4))


(def row-date (pan/date_range :start "2000-01-01" :end "2000-01-01"))
(get-item (get-attr table :loc) row-date)
