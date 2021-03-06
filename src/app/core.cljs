(ns app.core
  (:require [kee-frame.core :as kf]
            [re-frame.core :as rf]
            [ajax.core :as http]
            [app.ajax :as ajax]
            [app.routing :as routing]
            [app.view :as view]))

(rf/reg-event-fx
  ::load-about-page
  (constantly nil))

(kf/reg-controller
  ::about-controller
  {:params (constantly true)
   :start  [::load-about-page]})

(rf/reg-sub
  :docs
  (fn [db _]
    (:docs db)))

(kf/reg-chain
  ::load-home-page
  (fn [_ _]
    {:http-xhrio {:method          :get
                  :uri             "https://raw.githubusercontent.com/beetleman/minimal-shadow-cljs-browser/master/README.md"
                  :response-format (http/raw-response-format)
                  :on-failure      [:common/set-error]}})
  (fn [{:keys [db]} [_ docs]]
    {:db (assoc db :docs docs)}))


(kf/reg-controller
  ::home-controller
  {:params (constantly true)
   :start  [::load-home-page]})

;; -------------------------
;; Initialize app
(defn mount-components []
  (rf/clear-subscription-cache!)
  (kf/start! {:debug?         true
              :routes         routing/routes
              :hash-routing?  false
              :initial-db     {}
              :root-component [view/root-component]}))

(defonce _ (ajax/load-interceptors!))

(defn ^:export main []
  (mount-components))
