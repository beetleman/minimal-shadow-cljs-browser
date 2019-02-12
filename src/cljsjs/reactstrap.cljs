(ns cljsjs.reactstrap
  (:require ["reactstrap" :as reactstrap]
            [goog.object :as gobj]))

(gobj/set js/window "Reactstrap" reactstrap)
