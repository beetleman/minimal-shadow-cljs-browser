(ns app.build-hook
  (:require [hiccup.core :refer [html]]))

(defn read-edn [path]
  (clojure.edn/read-string (slurp path)))

(defn get-root [build-state]
  (get-in build-state [:shadow.build/config :output-dir]))

(defn get-manifest [build-state]
  (read-edn (str (get-root build-state)
                 "/"
                 "manifest.edn")))

(defn index [manifest]
  (html
   [:html
    [:body
     [:div#app "loading..."]

     [:link
      {:href "https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
       :rel  "stylesheet"}]

     [:script {:src (str "js/" (-> manifest first :output-name))}]
     [:link {:href "/css/screen.css" :rel "stylesheet"}]]]))


(defn write-index-html [build-state options]
  (let [manifest   (get-manifest build-state)
        index-html (index manifest)]
    (spit (:index-html-path options)
          index-html)))

(defn hook
  {:shadow.build/stage :flush}
  [build-state options]
  (write-index-html build-state options)
  build-state)
