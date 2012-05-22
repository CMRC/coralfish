(ns coralfish.views.welcome
  (:require [coralfish.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [net.cgrand.enlive-html])
  (:import java.net.URL))

defn gis
  [rest]
  (transform (html-resource (URL. (str "http://coralfish.ucc.ie/" rest)))
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
"}")})))

(defpage "/noir" []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis))})

(defpage ["/noir/:rest" :rest #".*$"]
  {:keys [rest]}
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis rest))})
