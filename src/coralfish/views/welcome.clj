(ns coralfish.views.welcome
  (:require [coralfish.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [net.cgrand.enlive-html])
  (:import java.net.URL))

(defn gis
  []
  (html-resource (URL. "http://coralfish.ucc.ie")))

(defpage "/noir" []
  (emit* (gis)))
