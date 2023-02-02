(require 'request)

(setq dev-endpoint "http://dummyjson.com/")

(defun rqdev(url)
  (format "%s%s" dev-endpoint url))

(request (rqdev "products/1")
  :parser 'json-read
  :success (cl-function
            (lambda (&key data &allow-other-keys)
              (progn
                (message "\n\ndata:%s " data)
                (message "wtf")))))

(plz 'get "https://github.com/alphapapa/plz.el#installation")
