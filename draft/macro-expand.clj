(ns user
  (:require [libpython-clj2.require :refer [require-python]]
            [libpython-clj2.python :refer [py. py.. py.-] :as py]
            ;; [libpython-clj2.python/numpy :as np]
            [tech.v3.datatype :as dtype]))


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


(require '[libpython-clj2.require :refer [require-python]])
(require '[libpython-clj2.python :refer [call-attr get-item get-attr py.]])
(require '[libpython-clj2.python :as py])
(require-python '[numpy :as np])
(require-python '[pandas :as pan])

(def dates (pan/date_range "1/1/2000" :periods 8))
(def table (pan/DataFrame (print (np/random :randn 8 4)) :index dates :columns ["A" "B" "C" "D"]))

(np/linspace 2 3 :num 10)
(py/py. np/random randn 4)
;; (defmacro a1 [fun res res2] `(py. np/random ~fun ~@(list res res2)))
(macroexpand `(a1 "randn" 4.534 sd))
(defmacro plus [a b] `(+ ~a ~b))
(macroexpand `(plus "kjsdf" 4))
(py. "np/random" "randn" 4)
(defn a1 [fun num1]
  (apply py. 'np/random fun num1))
(a1 :randn 4)
(def fun 33)
(str fun)
(defmacro report
  ;; & args to make arg a list
  [& to-try]
  `(if ~to-try
     to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))
(defmacro report1.5
  [to-try]
  `(if ~'to-try
     to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))
(macroexpand `(report (= 1 1)))
(macroexpand `(report1.5 (= 1 1)))
(defmacro report2
  [to-try]
  `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful:" result#)
       (println (quote ~to-try) "was not successful:" result#))))
(macroexpand `(report (=)))
(macroexpand `(report2 (1)))
(macroexpand `(a1 randn 4 3))


(def row-date (pan/date_range :start "2000-01-01" :end "2000-01-01"))
(get-item (get-attr table :loc) row-date)

(def context {:response {:headers "wtf" :w {:a "abc"} :arr ["wtf"]
                         "wtf" :a}})
(get-in context [:response "wtf"])
