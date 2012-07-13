(ns coralfish.views.welcome
  (:require [compojure.handler :as handler]
	    [compojure.route :as route])
  (:use [hiccup.core :only [html]]
        [net.cgrand.enlive-html]
	[compojure.core]
	[ring.adapter.jetty]
	[coralfish.models.R])
  (:import java.net.URL 
           java.net.URLEncoder))

(defn gis
  [mykeys]
  (let [{:keys [rest layer]} mykeys
        keys2 (dissoc mykeys :layer)
        layers (apply str (interpose \& (map #(str "layer[]=" %) layer)))
        param_str (apply str (interpose \& (map #(str (name (key %)) "=" (val %)) keys2)))
        url (URL. (str "http://coralfish.ucc.ie/" rest ".php?" param_str "&" layers))
	p (prn mykeys)]
    (-> (transform (html-resource url) 
		   [:#mapserverForm :> :input]
		   (fn [a-node] (let [id (keyword (:id (:attrs a-node)))
				      par (when id (id mykeys))
				      pp (prn id par)]
				  (assoc-in a-node [:attrs :value] par))))
        (transform 
	 [:#infobar]
	 (content (apply str (interpose ", " layer))))
	(transform
	 [:head :link]
	 (set-attr :href "/noir/css/atlas.css"))
	(transform
	 [:form#mapserverForm :> :table]
	 (after {:tag :img
		 :attrs {:id "analysis"
			 :src "http://www.climateireland.ie:8080/plot/Cork/DJF/T_2M"}})))))

(defroutes main-routes
  (route/resources "/noir")
  (GET "/noir/:rest.php" {params :params}
       {:status 200
	:headers {"Content-Type" "text/html"}
	:body (emit* (gis params))})
  (route/not-found "<h1>Page not found</h1>"))

(run-jetty (handler/site main-routes) {:port 8729})