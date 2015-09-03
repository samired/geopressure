; Collection of GUI functions together

(defmacro on-action [component event & body]
  `(. ~component addActionListener
      (proxy [java.awt.event.ActionListener] []
        (actionPerformed [~event] ~@body))))


(defn get-drlg-file [ ]
  (let [extFilter (FileNameExtensionFilter. "Text File" (into-array  ["txt" "md" "xls" "csv"]))
        filechooser (JFileChooser. "C:/")
        dummy (.setFileFilter filechooser extFilter)
        retval (.showOpenDialog filechooser nil) ]
    (if (= retval JFileChooser/APPROVE_OPTION)
      ((def drlg-data(read-dataset (.getSelectedFile filechooser) :header true))
       view drlg-data)
      "")))


(defn get-gas-file [ ]
  (let [extFilter (FileNameExtensionFilter. "Text File" (into-array  ["txt" "md" "xls" "csv"]))
        filechooser (JFileChooser. "C:/")
        dummy (.setFileFilter filechooser extFilter)
        retval (.showOpenDialog filechooser nil) ]
    (if (= retval JFileChooser/APPROVE_OPTION)
      (def gas-data(read-dataset (.getSelectedFile filechooser) :header true))
      "")))

