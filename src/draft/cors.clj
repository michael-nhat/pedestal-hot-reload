(ns draft.cors
  (:require [clojure.test :as test :refer [are deftest is testing]]
            [ring.middleware.cors :as cors]))

(defn handler [& args]
  (apply
   (cors/wrap-cors
    (fn
      ([request] {})
      ([request respond raise]
       (respond request)))
    :access-control-allow-origin #"http://example.com"
    :access-control-allow-headers #{:accept :content-type}
    :access-control-allow-methods #{:get :put :post})
   args))

(deftest test-preflight
  (testing "whitelist concrete headers"
    (let [headers {"origin" "http://example.com"
                   "access-control-request-method" "POST"
                   "access-control-request-headers" "Accept, Content-Type"}]
      (is (= {:status 200,
              :headers {"Access-Control-Allow-Origin" "http://example.com"
                        "Access-Control-Allow-Headers" "Accept, Content-Type"
                        "Access-Control-Allow-Methods" "GET, POST, PUT"}
              :body "preflight complete"}
             (handler {:request-method :options
                       :uri "/"
                       :headers headers})))))

  (testing "whitelist any headers"
    (is (= {:status 200,
            :headers {"Access-Control-Allow-Origin" "http://example.com"
                      "Access-Control-Allow-Headers" "X-Bar, X-Foo"
                      "Access-Control-Allow-Methods" "GET, POST, PUT"}
            :body "preflight complete"}
           ((cors/wrap-cors (fn [_] {})
                            :access-control-allow-origin #"http://example.com"
                            :access-control-allow-methods #{:get :put :post})
            {:request-method :options
             :uri "/"
             :headers {"origin" "http://example.com"
                       "access-control-request-method" "POST"
                       "access-control-request-headers" "x-foo, x-bar"}}))))

  (testing "whitelist headers ignore case"
    (is (= (handler {:request-method :options
                     :uri "/"
                     :headers {"origin" "http://example.com"
                               "access-control-request-method" "POST"
                               "access-control-request-headers"
                               "ACCEPT, CONTENT-TYPE"}})
           {:status 200
            :headers {"Access-Control-Allow-Origin" "http://example.com"
                      "Access-Control-Allow-Headers" "Accept, Content-Type"
                      "Access-Control-Allow-Methods" "GET, POST, PUT"}
            :body "preflight complete"})))

  (testing "method not allowed"
    (is (empty? (handler
                 {:request-method :options
                  :uri "/"
                  :headers {"origin" "http://example.com"
                            "access-control-request-method" "DELETE"}}))))

  (testing "header not allowed"
    (let [headers {"origin" "http://example.com"
                   "access-control-request-method" "GET"
                   "access-control-request-headers" "x-another-custom-header"}]
      (is (empty? (handler
                   {:request-method :options
                    :uri "/"
                    :headers headers}))))))

{"extension" "Security/Digest Security/SSL",
 "accept-charset" " utf-8 ;q=1, gb2312;q=0.5, iso-8859-1;q=0.5, big5;q=0.5, iso-2022-jp;q=0.5, shift_jis;q=0.5, euc-tw;q=0.5, euc-jp;q=0.5, euc-jis-2004;q=0.5, euc-kr;q=0.5, us-ascii;q=0.5, utf-7;q=0.5, hz-gb-2312;q=0.5, big5-hkscs;q=0.5, gbk;q=0.5, gb18030;q=0.5, iso-8859-5;q=0.5, koi8-r;q=0.5, koi8-u;q=0.5, cp866;q=0.5, koi8-t;q=0.5, windows-1251;q=0.5, cp855;q=0.5, iso-8859-2;q=0.5, iso-8859-3;q=0.5, iso-8859-4;q=0.5, iso-8859-9;q=0.5, iso-8859-10;q=0.5, iso-8859-13;q=0.5, iso-8859-14;q=0.5, iso-8859-15;q=0.5, windows-1250;q=0.5, windows-1252;q=0.5, windows-1254;q=0.5, windows-1257;q=0.5, cp775;q=0.5, cp850;q=0.5, cp852;q=0.5, cp857;q=0.5, cp858;q=0.5, cp860;q=0.5, cp861;q=0.5, cp863;q=0.5, cp865;q=0.5, cp437;q=0.5, macintosh;q=0.5, next;q=0.5, hp-roman8;q=0.5, adobe-standard-encoding;q=0.5, iso-8859-16;q=0.5, iso-8859-7;q=0.5, windows-1253;q=0.5, cp737;q=0.5, cp851;q=0.5, cp869;q=0.5, iso-8859-8;q=0.5, windows-1255;q=0.5, cp862;q=0.5, iso-2022-jp-2004;q=0.5, cp874;q=0.5, iso-8859-11;q=0.5, viscii;q=0.5, windows-1258;q=0.5, iso-8859-6;q=0.5, windows-1256;q=0.5, iso-2022-cn;q=0.5, iso-2022-cn-ext;q=0.5, iso-2022-jp-2;q=0.5, iso-2022-kr;q=0.5, utf-16le;q=0.5, utf-16be;q=0.5, utf-16;q=0.5, x-ctext;q=0.5",
 "accept" "*/*",
 "connection" "keep-alive",
 "host" "localhost:8081",
 "content-length" "0",
 "mime-version" "1.0"}


(def a "mig_clj,postgres,postgres")
