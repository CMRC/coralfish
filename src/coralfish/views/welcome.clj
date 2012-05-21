(ns coralfish.views.welcome
  (:require [coralfish.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [net.cgrand.enlive-html])
  (:import java.net.URL))

(defn gis
  []
  (transform (html-resource (URL. "http://coralfish.ucc.ie"))
             [:body]
             (append {:tag :img :attrs {:src "http://www.climateireland.ie:8080/plot/Kilkenny/MAM/T_2M/decadal"}})))

(defpage "/noir" []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (emit* (gis))})
