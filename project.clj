(defproject coralfish "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
			   [compojure "1.1.0"]
                           [enlive "1.0.0"]]
	    :dev-dependencies [[lein-ring "0.4.5"]]
	    :ring {:handler coralfish.views.welcome}
	    :main coralfish.views.welcome)

