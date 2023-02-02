
(require 'plz)

(setq dumm-url "https://dummyjson.com/products/search?q=phone")

(defun wtf-buffer () (with-temp-buffer (insert (plz 'get dumm-url))
                                       (print (current-buffer))
                                       (json-pretty-print-buffer)
                                       (buffer-string)))

(message "%s" (wtf-buffer))

;; (setq wtf (plz 'get dumm-url))

;; (insert-buffer-substring-as-yank wtf)

;; (insert "wtf")

;; (plz 'get "https://dummyjson.com/products/search?q=phone")
