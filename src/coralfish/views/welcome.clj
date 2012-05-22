(ns coralfish.views.welcome
  (:require [coralfish.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [noir.response :only [redirect]]
        [hiccup.core :only [html]]
        [net.cgrand.enlive-html])
  (:import java.net.URL))

(defn gis
  [keys]
  (let [{:keys [rest layer]} keys
        keys2 (dissoc keys :layer)
        layers (apply str (interpose \& (map #(str "layer[]=" %) layer)))
        param_str (apply str (interpose \& (map #(str (name (key %)) "=" (val %)) keys2)))
        url (URL. (str "http://coralfish.ucc.ie/index.php?" param_str "&" layers))]
	(println url)
    (transform (html-resource url) 
               [:head]
               (append {:tag :script 
                        :attrs {:type "text/javascript"}
                        :content (str 
                                 "function onShowInfoLayerClick ( layerID ) {"
                                    "windowURL = 'http://www.climateireland.ie:8080/svg/compare/2021/2035/J2D/T_2M';"
                                    "window_handle = window.open( windowURL, 'popupDOI',"
                                    "'toolbar=no,status=yes,resizable=yes,scrollbars=yes,width=800,height=700,top=160,left=160' );"
                                    "window_handle.focus();"
                                    "return false;"
                                    "}")}))))

(defpage "/noir" []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis))})

(defpage [:get ["/noir/:rest" :rest #"(index|mode)\.php.*"]]
  keys
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis keys))})

(defpage [:get ["/noir/:first/:rest" :rest #".*$"]]
  {:keys [first rest]}
  (redirect (str "/" first "/" rest)))
