(ns coralfish.views.welcome
  (:require [coralfish.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [noir.response :only [redirect]]
        [hiccup.core :only [html]]
        [net.cgrand.enlive-html])
  (:import java.net.URL 
           java.net.URLEncoder))

(defn gis
  [keys]
  (let [{:keys [rest layer]} keys
        keys2 (dissoc keys :layer)
        layers (apply str (interpose \& (map #(str "layer[]=" %) layer)))
        param_str (apply str (interpose \& (map #(str (name (key %)) "=" (val %)) keys2)))
        url (URL. (str "http://coralfish.ucc.ie/" rest "?" param_str "&" layers))]
    (transform (html-resource url) 
               [:#mapserverForm :> :input]
               (fn [a-node] (let [id (keyword (:id (:attrs a-node)))
                                  par (when id (id keys))]
                                 (println id " = " par)
                                     (assoc-in a-node [:attrs :value] par))))))

(defpage "/noir" []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis))})

(defpage [:get ["/noir/:rest" :rest #".*\.php"]]
  keys
  (println keys)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis keys))})

(defpage [:get ["/noir/:first/:rest" :rest #".*$"]]
  {:keys [first rest]}
  (redirect (str "/" first "/" rest)))

(defpage [:get ["/noir/:first/:second/:rest" :rest #".*$"]]
  {:keys [first second rest]}
  (redirect (str "/" first "/" second "/" rest)))
