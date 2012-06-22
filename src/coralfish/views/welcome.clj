(ns coralfish.views.welcome
  (:require [compojure.handler :as handler]
	    [compojure.route :as route])
  (:use [hiccup.core :only [html]]
        [net.cgrand.enlive-html]
	[compojure.core]
	[ring.adapter.jetty])
  (:import java.net.URL 
           java.net.URLEncoder))

(defn gis
  [mykeys]
  (let [{:keys [rest layer]} mykeys
        keys2 (dissoc mykeys :layer)
        layers (apply str (interpose \& (map #(str "layer[]=" %) layer)))
        param_str (apply str (interpose \& (map #(str (name (key %)) "=" (val %)) keys2)))
        url (URL. (str "http://coralfish.ucc.ie/" rest "?" param_str "&" layers))
	p (println mykeys)]
    (-> (transform (html-resource url) 
		   [:#mapserverForm :> :input]
		   (fn [a-node] (let [id (keyword (:id (:attrs a-node)))
				      par (when id (id mykeys))]
				  (assoc-in a-node [:attrs :value] par))))
        (transform 
	 [:#infobar]
	 (content (apply str (interpose ", " layer))))
	(transform
	 [:head :link]
	 (set-attr :href "/noir/css/atlas.css")))))

(defroutes main-routes
  (route/resources "/noir/css" {:root "public/css"})
  (GET "/noir/index.php" [rest]
       {:status 200
	:headers {"Content-Type" "text/html"}
	:body (emit* (gis rest))})
  (GET ["/noir/:rest" :rest #".*"] [rest]
       {:status 302
	  :headers {"Location" (str "/" rest)}})
  (GET "/noir/:first/:second/:rest"
       [first second rest]
       {:status 302
	:headers {"Location" (str "/" first "/" second "/" rest)}})
  (route/not-found "<h1>Page not found</h1>"))

(run-jetty (handler/site main-routes) {:port 8729})