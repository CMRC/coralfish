(defproject coralfish "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
			   [compojure "1.1.0"]
                           [enlive "1.0.0"]
			   [incanter "1.2.3"]]
	    :native-dependencies [[jriengine "0.8.4"]]
	    :dev-dependencies [[lein-ring "0.4.5"]
			       [native-deps "1.0.5"]]
	    :ring {:handler coralfish.views.welcome}
	    :git-dependencies [["https://github.com/jolby/rincanter.git"]]
	    :extra-classpath-dirs [".lein-git-deps/rincanter/src/"]
	    :main coralfish.views.welcome)

