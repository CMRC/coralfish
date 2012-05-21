(ns coralfish.server
  (:require [noir.server :as server]))

(server/load-views "src/coralfish/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8729"))]
    (server/start port {:mode mode
                        :ns 'coralfish})))

