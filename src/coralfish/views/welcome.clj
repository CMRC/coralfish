(ns coralfish.views.welcome
  (:require [coralfish.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [net.cgrand.enlive-html]]))

(defpage "/noir" []
         (common/layout
           [:p "Welcome to coralfish"]))
