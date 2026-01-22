document.addEventListener("DOMContentLoaded", () => {

const recipeMap = {
  "Shenga Undi (Ladoo)": "ಶೇಂಗಾ ಉಂಡಿ (ಲಡ್ಡು)",
  "Kallu Thambittunde": "ಕಲ್ಲು ತಂಬಿಟ್ಟು ಉಂಡೆ",
  "Aralu Unde (Puffed Rice Ladoo)": "ಅರಳು ಉಂಡೆ",
  "Kobbari Mithai (Coconut Burfi)": "ಕೊಬ್ಬರಿ ಮಿಠಾಯಿ (ತೆಂಗಿನ ಬರ್ಫಿ)",
  "Jolada Rotti": "ಜೋಳದ ರೊಟ್ಟಿ",
  "Besan Undi(Ladoo)": "ಬೇಸನ್ ಉಂಡಿ (ಲಾಡೂ)",
  "Mysore Pak": "ಮೈಸೂರು ಪಾಕ್",
  "Jalebi": "ಜಲೇಬಿ",
  "Jalebi Rabadi": "ಜಲೇಬಿ ರಬಡಿ",
  "Halbai rice halwa": "ಹಲ್‌ಬೈ (ಅಕ್ಕಿ-ತೆಂಗಿನ ಸಿಹಿ)",
  "Ragi Mudde": "ರಾಗಿ ಮುದ್ದೆ",
  "Gasagase Payasa with sugar": "ಗಸಗಸೆ ಪಾಯಸ (ಸಕ್ಕರೆ)",
  "Shavige Payasa": "ಶಾವಿಗೆ ಪಾಯಸ",
  "Akki Payasa": "ಅಕ್ಕಿ ಪಾಯಸ",
  "Gasagase Payasa with jaggery": "ಗಸಗಸೆ ಪಾಯಸ (ಬೆಲ್ಲ)",
  "Methi Rotti": "ಮೆಂತ್ಯೆ ರೊಟ್ಟಿ",
  "Nuchchu Ganji": "ನುಚ್ಚು ಗಂಜಿ",
  "Alugadde Palya (Potato)": "ಆಲುಗಡ್ಡೆ ಪಲ್ಯ",
  "Huruli Palya (Horsegram)": "ಹುರಳಿ ಪಲ್ಯ",
  "Thalipeeth": "ತಾಳೀಪೀಠ್ (ಉತ್ತರ ಕರ್ನಾಟಕ)",
  "Garlic Rotti": "ಬೆಳ್ಳುಳ್ಳಿ ರೊಟ್ಟಿ",
  "Kadubu (Steamed Dumpling)": "ಕಡುಬು",
  "Kadubu in Saaru": "ಕಡುಬು ಸಾರಿನಲ್ಲಿ",
  "Jolada Ambli": "ಜೋಳದ ಅಂಬ್ಲಿ",
  "Sajji Ganji": "ಸಜ್ಜಿ ಗಂಜಿ",
  "Tomato Bath": "ಟೊಮೇಟೋ ಬಾತ್",
  "Mosaru Anna (Curd Rice)": "ಮೋಸರು ಅನ್ನ",
  "Menthya Bath": "ಮೆಂಥ್ಯ ಬಾತ್",
  "Rotti Pakoda": "ರೊಟ್ಟಿ ಪಕೋಡ",
  "Chitranna (Lemon Rice)": "ಚಿತ್ರನ್ನ",
  "Huli Anna (Sambar Rice)": "ಹುಳಿ ಅನ್ನ",
  "Onion Rotti": "ಇರುಳ್ಳಿ ರೊಟ್ಟಿ",
  "Chapati Chips": "ಚಪಾತಿ ಚಿಪ್ಸ್",
  "Vegetable Bath": "ವೆಜಿಟೇಬಲ್ ಬಾತ್",
  "Kayi Chitranna (Coconut Rice)": "ಕಾಯಿ ಚಿತ್ರನ್ನ",
  "Bisi Bele Bath": "ಬಿಸಿ ಬೆಳೆ ಬಾತ್",
  "Puliyogare": "ಪುಲಿಯೋಗರೆ",
  "Rava Upittu (Upma)": "ರವ ಅಉಪಿಟ್ಟು",
  "Shavige Uppittu": "ಶಾವಿಗೆ ಉಪ್ಪಿಟ್ಟು",
  "Kharabath": "ಖಾರಾಬಾತ್",
  "Chow Chow Bath": "ಚೌ ಚೌ ಬಾತ್",
  "Kesari Bath": "ಕೇಸರಿ ಬಾತ್",
  "Vegetable Pulav": "ವೆಜಿಟೇಬಲ್ ಪುಲಾವ್",
  "Masala Rice": "ಮಸಾಲೆ ಅನ್ನ",
  "Ghee Rice": "ತುಪ್ಪದ ಅನ್ನ",
  "Thatte Idli": "ತಟ್ಟೆ ಇಡ್ಲಿ",
  "Set Dose": "ಸೆಟ್ ಡೋಸೆ",
  "Benne Dose": "ಬೆಣ್ಣೆ ಡೋಸೆ",
  "Sajji Dosa (Millet Dosa)": "ಸಜ್ಜಿ ಡೋಸೆ (ಮಿಲ್ಲೆಟ್ ಡೋಸೆ)",
  "Badanekayi Palya (Brinjal)": "ಬದನೆಕಾಯಿ ಪಲ್ಯ",
  "Bendekai Palya (Okra)": "ಬೆಂಡೆಕಾಯಿ ಪಲ್ಯ",
  "Tindli Palya (Ivy Gourd)": "ತಿಂಡ್ಲಿ ಪಲ್ಯ",
  "Capsicum Bath": "ಕ್ಯಾಪ್ಸಿಕಮ್ ಬಾತ್",
  "Sajji Idli (Millet Idli)": "ಸಜ್ಜಿ ಇಡ್ಲಿ",
  "Rotti Happala": "ರೊಟ್ಟಿ ಹಪ್ಪಳ",
  "Vangi Bath (Brinjal Rice)": "ವಾಂಗಿ ಬಾತ್",
  "Southekai Palya (Cucumber)": "ಸೌತೇಕಾಯಿ ಪಲ್ಯ",
  "Seemebadanekayi Palya (Chayote)": "ಸೀಮೆಬದನೆಕಾಯಿ ಪಲ್ಯ",
  "Avarekalu Usli Palya": "ಅವರೆಕಾಳು ಉಸ್ಲಿ ಪಲ್ಯ",
  "Sorekai Palya (Bottle Gourd)": "ಸೋರೇಕಾಯಿ ಪಲ್ಯ",
  "Kumbalakayi Palya (Pumpkin)": "ಕುಂಭಳಕಾಯಿ ಪಲ್ಯ",
  "Hagalkayi Palya (Bitter Gourd)": "ಹಾಗಳಕಾಯಿ ಪಲ್ಯ",
  "Heerekai Palya (Ridge Gourd)": "ಹೀರೇಕಾಯಿ ಪಲ್ಯ",
  "Gajjari Palya (Carrot)": "ಗಾಜರಿ ಪಲ್ಯ",
  "Beans Palya": "ಬೀನ್ಸ್ ಪಲ್ಯ",
  "Capsicum-Peanut Gojju": "ಕ್ಯಾಪ್ಸಿಕಂ-ಕಾಯಿಪುರಿ ಗೊಜ್ಜು",
  "Soppina Bassaru": "ಸೊಪ್ಪಿನ ಬಾಸ್ಸರು",
  "Harive Saaru (Amaranth)": "ಹರಿವೆ ಸಾರು",
  "Dantina Soppu Saaru": "ದಂತಿನ ಸೊಪ್ಪಿನ ಸಾರು",
  "Majjige Huli (Ash Gourd)": "ಮಜ್ಜಿಗೆ ಹುಳಿ (ಕುಂಬಳಕಾಯಿ)",
  "Majjige Huli (Cucumber)": "ಮಜ್ಜಿಗೆ ಹುಳಿ (ಸೌತೇಕಾಯಿ)",
  "Mixed Dal Saaru": "ಮಿಕ್ಸ್ಡ್ ದಾಲ್ ಸಾರು",
  "Kadle Kaalu Saaru": "ಕಡಲೆಕಾಳು ಸಾರು",
  "Tomato-Methi Gojju": "ಟೊಮೇಟೋ-ಮೆಥಿ ಗೊಜ್ಜು",
  "Southekai Majjige Kootu": "ಸೌತೇಕಾಯಿ ಮಜ್ಜಿಗೆ ಕೂಟು",
  "Huruli Sambar": "ಹುರಳಿ ಕೂಟು",
  "Sabbasige Saaru": "ಸಬ್ಬಸಿಗೆ ಸಾರು",
  "Thove (Ridge Gourd)": "ತೋವೆ (ಹೀರೇಕಾಯಿ)",
  "Baalekayi Palya": "ಬಾಳೆಕಾಯಿ ಪಲ್ಯ",
  "Kadle Kaalu Palya": "ಕಡಲೆಕಾಳು ಪಲ್ಯ",
  "Batani Sagu": "ಬಟಾಣಿ ಸಾಗು",
  "Pundi Palya": "ಪುಂಡಿ ಪಲ್ಯ",
  "Masala Dosa": "ಮಸಾಲೆ ದೋಸೆ ",
  "Rava Dosa": "ರವಾ ದೋಸೆ",
  "Neer Dosa": "ನೀರ್ ದೋಸೆ",
  "Ragi Dosa": "ರಾಗಿ ದೋಸೆ",
  "Gojju Avalakki": "ಗೊಜ್ಜು ಅವಳಕ್ಕಿ",
  "Avalakki Oggarane": "ಅವಳಕ್ಕಿ ಒಗ್ಗರಣೆ",
  "Mosaru Avalakki": "ಮೊಸರು ಅವಳಕ್ಕಿ",
  "Mandakki Oggarane": "ಮಂಡಕ್ಕಿ ಒಗ್ಗರಣೆ",
  "Nuggekayi(Drumstick) Sambar": "ನುಗ್ಗೆಕಾಯಿ ಸಾಂಬಾರ್",
  "Plain Dosa": "ಸಾಧಾರಣ ದೋಸೆ",
  "Girmit": "ಚುರುಮುರಿ",
  "Paddu with sambar & chutney": "ಪಡ್ಡು (ಗುಂಡಪೊಂಗಲು)",
  "Vegetable Sagu": "ತರಕಾರಿ ಸಾಗು",
  "Menasinakai Bajji (Mirchi)": "ಮೆಣಸಿನಕಾಯಿ ಬಜ್ಜಿ (ಮಿರ್ಚಿ)",
  "Aloo Bonda": "ಆಲೂ ಬೋಂಡ",
  "Onion Pakoda": "ಈರುಳ್ಳಿ ಪಕೋಡಾ",
  "Cabbage Pakoda": "ಕೆಬ್ಬೇಜ್ ಪಕೋಡಾ",
  "Raw Banana Bajji": "ಕಾಯಿಯ ಬನಾನಾ ಬಜ್ಜಿ",
  "Brinjal Bajji": "ವಂಗಿ ಬಜ್ಜಿ",
  "Uddina Vada": "ಉದ್ದಿನ ವಡೆ",
  "Masala Vada (Kadale)": "ಮಸಾಲಾ ವಡೆ (ಕಡಲೆ)",
  "Bonda Soup": "ಬೋಂಡ ಸೌಪ್",
  "Idli Sambar": "ಇಡ್ಲಿ ಸಾಂಬಾರ್",
  "Thatte Idli Chutney": "ತಟ್ಟೆ ಇಡ್ಲಿ ಚಟ್ನಿ",
  "Rava Idli": "ರವಾ ಇಡ್ಲಿ",
  "Chakli": "ಚಕ್ಲಿ",
  "Kodubale": "ಕೊಡುಬಳೆ",
  "Nippattu": "ನಿಪ್ಪಟ್ಟು",
  "Karchikai (Karanji Sweet)": "ಕಾರಂಜಿ (ಮಿಠಾಯಿ)",
  "Heerekayi Bajji": "ಹೀರೇಕಾಯಿ ಬಜ್ಜಿ",
  "Sajjige (Sooji Halwa Breakfast)": "ಸಜ್ಜಿಗೆ (ರವೆ ಹಲ್ವಾ)",
  "Bele Saaru": "ಬೇಳೆ ಸಾರು",
  "Tomato Saaru": "ಟೊಮೇಟೊ ಸಾರು",
  "Karigadubu (Sweet Snack)": "ಕರಿಗಡಬು (ಸಿಹಿ ತಿನಿಸು)",
  "Menthya Pulao": "ಮೆಂತ್ಯ ಪುಲಾವ್",
  "Dharwad Peda": "ಧಾರವಾಡ ಪೇಡಾ",
  "Belagavi Kunda": "ಬೆಳಗಾವಿ ಕುಂಡಾ",
  "Gokak Karadantu": "ಗೋಮಕ ಕರದಂತು",
  "Mandakki Chikki": "ಮಂಡಕ್ಕಿ ಚಿಕ್ಕಿ",
  "Ellu Chikki": "ಎಳ್ಳು ಚಿಕ್ಕಿ",
  "Shenga Holige": "ಶೇಂಗ ಹೋಳಿಗೆ",
  "Bele Holige": "ಬೇಳೆ ಹೋಳಿಗೆ",
  "Kayi Holige": "ಕಾಯಿ ಹೋಳಿಗೆ",
  "Chiroti": "ಚಿರೋಟಿ",
  "Sajjappa": "ಸಜ್ಜಪ್ಪ",
  "Sabakki Vada (Sago)": "ಸಬ್ಬಕ್ಕಿ ವಡೆ",
  "Ellu Holige": "ಎಳ್ಳು ಹೋಳಿಗೆ",
  "Pudina Pulao": "ಪುಡೀನಾ ಪುಲಾವ್",
  "Rava Ladoo": "ರವೆ ಲಾಡು",
  "Kadle Chikki": "ಕಡಲೆ ಚಿಕ್ಕಿ",
  "Haalu Holige": "ಹಾಲು ಹೋಳಿಗೆ",
  "Medu Vada Curd": "ಮೇಡು ವಡೆ ತುಪ್ಪ ಹಾಕಿದ",
  "Kosu Palya (Cabbage)": "ಕೋಸು ಪಲ್ಯ",
  "Hasi Mensinkai Palya (Green Chili)": "ಹಸಿ ಮೆಣಸಿನಕಾಯಿ ಪಲ್ಯ",
  "Batani Palya (Green Peas)": "ಬಟಾಣಿ ಪಲ್ಯ",
  "Tomato Palya": "ಟೊಮೇಟೊ ಪಲ್ಯ",
  "Soppina Huli (Mixed Greens)": "ಸೊಪ್ಪಿನ ಹೂಳಿ",
  "Badanekayi Huli": "ಬದನೆಕಾಯಿ ಹೂಳಿ",
  "Bendekai Huli": "ಬೆಂಡೆಕಾಯಿ ಹೂಳಿ",
  "Southekai Huli": "ಸೌತೆಕಾಯಿ ಹೂಳಿ",
  "Sorekai Huli": "ಸೋರೆಕಾಯಿ ಹೂಳಿ",
  "Alasande Huli (Black-eyed Beans)": "ಅಲಸಂದೆ ಹೂಳಿ",
  "Avarekai Huli": "ಅವರೇಕಾಯಿ ಹೂಳಿ",
  "Togari Bele Huli": "ತೊಗರಿ ಬೇಳೆ ಹೂಳಿ",
  "Donne Menasinakai Palya": "ಡೋನ್ನೆ ಮೆಣಸಿನಕಾಯಿ ಪಲ್ಯ",
  "Vegetable Sagu": "ತರಕಾರಿ ಸಾಗು",
  "Lemon Saaru": "ನಿಂಬೆ ಸಾರು",
  "Garlic Saaru": "ಬೆಳ್ಳುಳ್ಳಿ ಸಾರು",
  "Pepper-Cumin Saaru": "ಮೆಣಸು-ಜೀರಿಗೆ ಸಾರು",
  "Huruli Saaru (Horsegram)": "ಹುರಳಿ ಸಾರು",
  "Bassaru (Greens Stock Curry)": "ಬಸ್ಸಾರು",
  "Kattina Saaru": "ಕಟ್ಟಿನ ಸಾರು",
  "Majjige Saaru": "ಮಜ್ಜಿಗೆ ಸಾರು",
  "Rasam Powder Saaru": "ರಸಮ್ ಪುಡಿ ಸಾರು",
  "Onion Gojju": "ಈರುಳ್ಳಿ ಗೊಜ್ಜು",
  "Pineapple Gojju (Festive)": "ಅನಾನಸ್ ಗೊಜ್ಜು",
  "Hasi Menasinakai Gojju": "ಹಸಿಮೆಣಸಿನಕಾಯಿ ಗೊಜ್ಜು",
  "Bendekai Gojju": "ಬೆಂಡೆಕಾಯಿ ಗೊಜ್ಜು",
  "Thove (Plain Dal)": "ತೋವೆ",
  "Mixed Veg Kootು": "ಮಿಶ್ರಣ ಸೊಪ್ಪು ಕೂಟು",
  "Sabbasige Soppು Kootು (Dill)": "ಸಬ್ಬಸಿಗೆ ಸೊಪ್ಪು ಕೂಟು",
  "Harive Soppು Kootು (Amaranth)": "ಹರಿವೆ ಸೊಪ್ಪು ಕೂಟು",
  "Ennegayi (Stuffed Brinjal)": "ಎಣ್ಣೆಗಾಯಿ",
  "Bharwa Badanekayi": "ಭರ್ತಾ ಬದನೆಕಾಯಿ",
  "Badanekayi Palya with Shenga": "ಬದನೆಕಾಯಿ ಪಾಲ್ಯ ಶೇಂಗಾ ಜೊತೆಗೆ",
  "Avalakki Payasa": "ಅವಳಕ್ಕಿ ಪಾಯಸ",
  "Badam Milk": "ಬಾದಾಮಿ ಹಾಲು",
  "Gulab Jamun": "ಗುಲಾಬ್ ಜಾಮುನ್",
  "Kasi Halwa (Ash Gourd)": "ಕಾಸಿ ಹಾಲ್ವಾ",
  "Pumpkin Halwa": "ಕದುಂಬಳ ಹಾಲ್ವಾ",
  "Dry Fruit Barfi": "ಸೂಕೆ ಹಣ್ಣು ಬರ್ಫಿ",
  "Nawabi Halwa": "ನೋವಾಬಿ ಹಾಲ್ವಾ",
  "Jaggery Phirni": "ಬೆಲ್ಲ ಫಿರ್ನಿ",
  "Milk Barfi": "ಮಿಲ್ಕ್ ಬರ್ಫಿ",
  "Peda": "ಪೇಡಾ",
  "Kesar Peda": "ಕೇಶರ್ ಪೇಡಾ",
  "Gajar Barfi": "ಗಾಜರ ಬರ್ಫಿ",
  "Carrot Halwa": "ಗಾಜರ ಹಾಲ್ವಾ",
  "Buns": "ಬನ್ಸ್",
  "Palak Bajji": "ಪಾಲಕ್ ಬಜ್ಜಿ",
  "Tuppada Avalakki": "ತುಪ್ಪದ ಅವಳಕ್ಕಿ",
  "Jolada Rotti Mutagi": "ಜೋಳದ ರೊಟ್ಟಿ ಮುತ್ತಗಿ",
  "Junaka": "ಜುನಕ",
  "Khara Kadubu": "ಖಾರ ಕಡಬು",
  "Happala (Papad) Fry": "ಹಪ್ಪಳ (ಪಾಪಡ್)",
  "Avalakki Mixture": "ಅವಲಕ್ಕಿ ಮಿಶ್ರಣ",
  "Mandakki Mixture": "ಮಂಡಕ್ಕಿ ಮಿಶ್ರಣ",
  "Kadlepuri Mixture": "ಕಡ್ಲೆಪುರಿ ಮಿಶ್ರಣ",
  "Gol Bajji": "ಗೋಲ್ ಬಜ್ಜಿ",
  "Rajgiri Palya": "ರಾಜಗಿರಿ ಪಲ್ಲೆ",
  "Sajji Rotti": "ಸಜ್ಜಿ ರೊಟ್ಟಿ",
  "Groundnut Sundal (Usli)": "ಕಡ್ಲೆ ಸುಂದಲ್ (ಉಸಳಿ)",
  "Kadle Usli (Chana)": "ಕಡ್ಲೆ ಉಸಳಿ (ಚಣಾ)",
  "Huruli Sundal": "ಹುರಳಿ ಸುಂದಲ್",
  "Avarekalu Sundal": "ಅವರೆಕಾಳು ಸುಂದಲ್",
  "Bread Bajji": "ಬ್ರೆಡ್ ಬಜ್ಜಿ",
  "Menthya Dosa": "ಮೆಂತ್ಯ ದೋಸೆ",
  "Ragi Idli": "ರಾಗಿ ಇಡ್ಲಿ",
  "Dates Laddu": "ಖರ್ಜೂರ ಲಡ್ಡು",
  "Ellu Unde (Sesame Ladoo)": "ಎಳ್ಳು ಉಂಡೆ",
  "Coconut Laddu": "ತೇಂಗಿನ ಲಡ್ಡು",
  "Shenga Chutney Pudi": "ಶೇಂಗಾ ಚಟ್ನಿ ಪುಡಿ",
  "Dry Coconut Chutney Pudi": "ಶುಷ್ಕ ತೇಂಗಿನ ಚಟ್ನಿ ಪುಡಿ",
  "Flaxseed (Agase) Chutney": "ಅಗಸೆ ಚಟ್ನಿ",
  "Niger Seed (Uchellu) Chutney": "ಉಚ್ಚೆಲು ಚಟ್ನಿ",
  "Ellು (Sesame) Chutney": "ಎಳ್ಳು ಚಟ್ನಿ",
  "Poori Sagu": "ಪೂರಿ ಸಾಗು",
  "Maddur Vada": "ಮದ್ದೂರು ವಡೆ",
  "Curry Leaf (Karibevು) Chutney": "ಕರಿಬೇವು ಚಟ್ನಿ",
  "Coriander Chutney": "ಕೊತ್ತಂಬರಿ ಚಟ್ನಿ",
  "Mint (Pudina) Chutney": "ಪುಡಿನ ಚಟ್ನಿ",
  "Tomato Chutney": "ಟೊಮ್ಯಾಟೋ ಚಟ್ನಿ",
  "Onion Chutney": "ಸೊಂಬು ಚಟ್ನಿ",
  "Coconut Chutney": "ತೆಂಗಿನ ಚಟ್ನಿ",
  "Green Chili Chutney": "ಹಸಿಮೆಣಸಿನ ಚಟ್ನಿ",
  "Garlic Chutney": "ಬೆಳ್ಳುಳ್ಳಿ ಚಟ್ನಿ",
  "Tamarind Chutney (Saarina)": "ಸಾರಿನ ಚಟ್ನಿ",
  "Groundnut + Til Chutney": "ಶೇಂಗಾ + ಎಳ್ಳು ಚಟ್ನಿ",
  "Saarina Pudi (Rasam Powder)": "ಸಾರಿನ ಪುಡಿ",
  "Huli Pudi (Sambar Powder)": "ಹುಳಿ ಪುಡಿ",
  "Puliyogare Gojju": "ಪುಲಿಯೋಗರೆ ಗೊಜ್ಜು",
  "Khara Pudi (Chili Powder)": "ಖರ ಪುಡಿ",
  "Menthya Chutney": "ಮೆಂತ್ಯ ಚಟ್ನಿ",
  "Til-Garlic Dry Chutney": "ಎಳ್ಳು-ಬೆಳ್ಳುಳ್ಳಿ ಸುಕ್ಕು ಚಟ್ನಿ",
  "Kadle Pudi (Gram Powder)": "ಕಡಲೆ ಪುಡಿ",
  "Roasted Red Chili Chutney": "ಹುರಿದ ಕೆಂಪು ಮೆಣಸಿನ ಚಟ್ನಿ",
  "Sandige (Sun-dried Fryums)": "ಸಂಡಿಗೆ",
  "Hesaru Kalu Palya (Green Gram)": "ಹೆಸರುಕಾಳು ಪಲ್ಯ",
  "Akki Rotti": "ಅಕ್ಕಿ ರೊಟ್ಟಿ (ಉತ್ತರ ಕರ್ನಾಟಕ ಶೈಲಿ)",
  "Badanekayi Gojju": "ಬದನೆಕಾಯಿ ಗೊಜ್ಜು",
};


  const recipes = Object.keys(recipeMap);
  const input = document.getElementById("recipeInput");
  const suggestionBox = document.getElementById("suggestionBox");
  const form = document.getElementById("recipeForm");

  // Display both English and Kannada side-by-side
  input.addEventListener("input", () => {
    const value = input.value.toLowerCase();
    suggestionBox.innerHTML = "";

    if (value) {
      const filtered = recipes.filter(
        (eng) =>
          eng.toLowerCase().includes(value) ||
          recipeMap[eng].toLowerCase().includes(value)
      );

      if (filtered.length) {
        // Header
        const header = document.createElement("div");
        header.classList.add("suggestion-header");
        header.innerHTML = "<span>English</span><span>Kannada</span>";
        suggestionBox.appendChild(header);

        // Grid container
        const grid = document.createElement("div");
        grid.classList.add("suggestion-grid");

        filtered.forEach((engName) => {
          const engDiv = document.createElement("div");
          const knDiv = document.createElement("div");

          engDiv.textContent = engName;
          knDiv.textContent = recipeMap[engName];

          engDiv.onclick = knDiv.onclick = () => {
            input.value = engName; // Always store English
            suggestionBox.style.display = "none";
          };

          engDiv.classList.add("english-column");
          knDiv.classList.add("kannada-column");

          grid.appendChild(engDiv);
          grid.appendChild(knDiv);
        });

        suggestionBox.appendChild(grid);
        suggestionBox.style.display = "block";
      } else {
        suggestionBox.style.display = "none";
      }
    } else {
      suggestionBox.style.display = "none";
    }
  });

  // Handle form submission
  form.addEventListener("submit", (e) => {
    e.preventDefault();
    const selectedRecipe = input.value.trim();

    if (selectedRecipe) {
      if (match(selectedRecipe)) {
        window.location.href = `recipe_details_byname.html?name=${encodeURIComponent(selectedRecipe)}`;
      } else {
        alert("Recipe not found or name modified.");
      }
    } else {
      alert("Please enter a recipe name.");
    }
  });

  // Validate English recipe name
  function match(selectedRecipe) {
    return recipes.some(
      (r) => r.toLowerCase() === selectedRecipe.trim().toLowerCase()
    );
  }

  // Hide suggestions if clicked outside
  document.addEventListener("click", (e) => {
    if (!e.target.closest(".search-container")) {
      suggestionBox.style.display = "none";
    }
  });

});
