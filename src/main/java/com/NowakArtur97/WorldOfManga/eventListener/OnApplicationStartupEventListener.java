package com.NowakArtur97.WorldOfManga.eventListener;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.language.Language;
import com.NowakArtur97.WorldOfManga.feature.language.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.language.LanguageService;
import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaRepository;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenreService;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
class OnApplicationStartupEventListener {

    private final static String TEST_PROFILE = "test";

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    private final ImageToByteConverter imageToByteConverter;

    private final LanguageService languageService;

    private final MangaGenreService mangaGenreService;

    private final MangaRepository mangaRepo;

    private static Language en;
    private static Language pl;

    @EventListener(ApplicationReadyEvent.class)
    void onStartup() throws LanguageNotFoundException, MangaGenreNotFoundException {

        en = languageService.findByLocale("en");
        pl = languageService.findByLocale("pl");

        MangaGenre action = mangaGenreService.findByEnglishTranslation("Action");
        MangaGenre adventure = mangaGenreService.findByEnglishTranslation("Adventure");
        MangaGenre comedy = mangaGenreService.findByEnglishTranslation("Comedy");
        MangaGenre drama = mangaGenreService.findByEnglishTranslation("Drama");
        MangaGenre demons = mangaGenreService.findByEnglishTranslation("Demons");
        MangaGenre fantasy = mangaGenreService.findByEnglishTranslation("Fantasy");
        MangaGenre historical = mangaGenreService.findByEnglishTranslation("Historical");
        MangaGenre magic = mangaGenreService.findByEnglishTranslation("Magic");
        MangaGenre martialArts = mangaGenreService.findByEnglishTranslation("Martial Arts");
        MangaGenre mature = mangaGenreService.findByEnglishTranslation("Mature");
        MangaGenre military = mangaGenreService.findByEnglishTranslation("Military");
        MangaGenre schoolLife = mangaGenreService.findByEnglishTranslation("School Life");
        MangaGenre shounen = mangaGenreService.findByEnglishTranslation("Shounen");
        MangaGenre sliceOfLife = mangaGenreService.findByEnglishTranslation("Slice of Life");
        MangaGenre supernatural = mangaGenreService.findByEnglishTranslation("Supernatural");
        MangaGenre superPower = mangaGenreService.findByEnglishTranslation("Super Power");
        MangaGenre psychological = mangaGenreService.findByEnglishTranslation("Psychological");
        MangaGenre romance = mangaGenreService.findByEnglishTranslation("Romance");
        MangaGenre horror = mangaGenreService.findByEnglishTranslation("Horror");

        String tokyoGhoulTitle = "Tokyo Ghoul";
        String tokyoGhoulDescriptionEn = "Ghouls live among us, the same as normal people in every way—except their craving for human flesh.\r\n"
                + "Ken Kaneki is an ordinary college student until a violent encounter turns him into the first half-human half-ghoul hybrid. Trapped between two worlds, he must survive Ghoul turf wars, learn more about Ghoul society and master his new powers. ";
        String tokyoGhoulDescriptionPl = "To tu skrywa się źródło \"rozpaczy\". Wtapiają się w tłum, polują na ludzi i pożerają zwłoki. Nazywa się je \"ghulami\". Losy młodzieńca, który przez przypadek styka się z taką istotą, zmieniają się całkowicie. Jego życie już nigdy nie będzie takie jak dawniej...";
        String tokyoGhoulAuthor = "Sui Ishida";

        String soloLevelingTitle = "Solo Leveling";
        String soloLevelingDescriptionEn = "10 years ago, after “the Gate” that connected the real world with the monster world opened, some of the ordinary, everyday people received the power to hunt monsters within the Gate. They are known as \"Hunters\". However, not all Hunters are powerful. My name is Sung Jin-Woo, an E-rank Hunter. I'm someone who has to risk his life in the lowliest of dungeons, the \"World's Weakest\". Having no skills whatsoever to display, I barely earned the required money by fighting in low-leveled dungeons… at least until I found a hidden dungeon with the hardest difficulty within the D-rank dungeons! In the end, as I was accepting death, I suddenly received a strange power, a quest log that only I could see, a secret to leveling up that only I know about! If I trained in accordance with my quests and hunted monsters, my level would rise. Changing from the weakest Hunter to the strongest S-rank Hunter!";
        String soloLevelingDescriptionPl = "10 lat temu, po \"Bramie\", która połączyła prawdziwy świat ze światem potworów, niektórzy zwykli ludzie otrzymali moc polowania na potwory w Bramie. Tacy ludzie są znani jako \"Łowcy\". Jednak nie wszyscy Łowcy są potężni. Nazywam się Sung Jin-Woo, łowca E-rank. Jestem kimś, kto musi zaryzykować życie w najniżej położonych lochach, \"Najsłabszych na świecie\". Nie mając żadnych umiejętności do pokazania, ledwo zarobiłem wymagane pieniądze, walcząc w lochach o niskim poziomie... przynajmniej dopóki nie znalazłem ukrytego lochu z najtrudniejszą przeszkodą w lochu D-rank! W końcu, gdy akceptowałem śmierć, nagle otrzymałem dziwną moc, dziennik misji, który tylko ja mogłem zobaczyć, sekret do wyexpienia, o którym tylko ja wiem! Gdybym trenował zgodnie z moimi zadaniami i ściganymi potworami, mój poziom powiększyłby się. Zmieniłbym się z najsłabszego Łowcy E-rank, do Najsilniejszego S-rank!";
        String soloLevelingAuthor = "Gee So-Lyung";

        String beastarsTitle = "Beastars";
        String beastarsDescriptionEn = "In a world populated by anthropomorphic animals, herbivores and carnivores coexist. For the adolescences of Cherryton Academy, school life is filled with hope, romance, distrust, and uneasiness.The mainPage character is Legoshi the wolf, a member of the drama club. Despite his menacing appearance, he has a very gentle heart. Throughout most of his life, he has always been an object to be feared and hated by other animals, and he has become quite accustomed to that lifestyle. But following the murder of a classmate, he finds himself becoming more involved with his other peers, who have their own share of insecurities, and finds his life in school changing slowly.";
        String beastarsDescriptionPl = "W świecie zamieszkałym przez antropomorficzne zwierzęta, gdzie roślinożercy i mięsożercy współpracują ze sobą, rozwija się Akademia Cherryton, szkoła wypełniona nadzieją, romansem, nieufnością i niepokojem. Główny bohater Legoshi jest wilkiem, a zarazem członkiem klubu teatralnego. Pomimo jego groźnego wyglądu, ma bardzo delikatne serce. Przez większość jego życia, inne zwierzęta bały się go, a niektóre nienawidziły, był całkiem przyzwyczajony do tego stylu życia, lecz nagle, zauważył, że staje się bardziej zaangażowany w relacje ze swoimi znajomymi z klasy, którzy dzielą się między sobą swoją niepewnością i złym samopoczuciem, wynikającym z bycia w pozycji w której są. Szkolne życie Legoshiego powoli zaczyna się zmieniać...";
        String beastarsAuthor = "Paru Itagaki";

        String myHeroAcademiaTitle = "My Hero Academia";
        String myHeroAcademiaDescriptionEn = "One day, a four-year-old boy came to a sudden realization: the world is not fair. Eighty percent of the world's population wield special abilities, known as \"quirks,\" which have given many the power to make their childhood dreams of becoming a superhero a reality. Unfortunately, Izuku Midoriya was one of the few born without a quirk, suffering from discrimination because of it. Yet, he refuses to give up on his dream of becoming a hero; determined to do the impossible, Izuku sets his sights on the elite hero training academy, UA High. However, everything changes after a chance meeting with the number one hero and Izuku's idol, All Might. Discovering that his dream is not a dead end, the powerless boy undergoes special training, working harder than ever before. Eventually, this leads to him inheriting All Might's power, and with his newfound abilities, gets into his school of choice, beginning his grueling journey to become the successor of the best hero on the planet.";
        String myHeroAcademiaDescriptionPl = "W niedalekiej przyszłości w ludziach obudziły się nadnaturalne zdolności. Od tej pory u kolejnych pokoleń moc pojawiała się częściej i była silniejsza. W tym momencie już ponad 80% populacji posiada swój własny dar. Z reguły ukazuje się on w okolicach czwartego roku życia. Izuku Midoriya jest wyjątkiem w tym świecie - choć ma już 14 lat, to nie posiada żadnych zdolności specjalnych. Co więcej, lekarze stwierdzili, że takowe się w nim nigdy nie przebudzą. Nadal jednak marzy on o zostaniu bohaterem. Pewnego dnia zostaje zaatakowany przez potwora i w ostatniej chwili ratuje go jego idol, najsilniejszy z bohaterów, zwany \"All Might\". Czy to spotkanie odmieni życie naszego młodego protagonisty? Czy uda mu się spełnić jego największe marzenie pomimo przeszkód stojących mu na drodze?";
        String myHeroAcademiaAuthor = "Kouhei Horikoshi";

        String komiTitle = "Komi Can't Communicate";
        String komiDescriptionEn = "Komi-san is the beautiful and admirable girl that no-one can take their eyes off her. Almost the whole school sees her as the cold beauty out of their league, but Tadano Shigeo knows the truth: she's just really bad at communicating with others. Komi-san, who wishes to fix this bad habit of hers, tries to improve it with the help of Tadano-kun.";
        String komiDescriptionPl = "Komi-san jest piękną i godną podziwu dziewczyną, od której nikt nie może oderwać oczu. Prawie cała szkoła postrzega ją jako zimne piękno, które jest poza ich zasięgiem, ale Tadano Hitohito zna prawdę: młoda piękność po prostu źle komunikuje się z innymi. Komi-san, chce to zmienić, a ma jej w tym pomóc Tadano.";
        String komiAuthor = "Tomohito Oda";

        String irumaTitle = "Mairimashita! Iruma-kun";
        String irumaDescriptionEn = "Fourteen-year-old Iruma Suzuki has been unfortunate all his life, having to work to earn money for his irresponsible parents despite being underage. One day, he finds out that his parents sold him to the demon Sullivan. However, Iruma's worries about what will become of him are soon relieved, for Sullivan merely wants a grandchild, pampering him and making him attend the demon school Babyls. At first, Iruma tries to keep a low profile in fear of his peers discovering that he is human. Unfortunately, this ends up being more difficult than he expected. It turns out that Sullivan himself is the chairman of the school, and everyone expects him to become the next Demon King! Iruma immediately finds himself in an outrageous situation when he has to chant a forbidden spell in front of the entire school. With this, Iruma instantly earns a reputation he does not want. Even so, he is bound to be roped into more bizarre circumstances.";
        String irumaDescriptionPl = "Co zrobić, gdy rodzice dla pieniędzy postanawiają zaprzedać duszę diabłu? A co gorsza - Twoją duszę? I ciało? W wyniku takiej właśnie niewdzięcznej transakcji czternastoletni Iruma zostaje zaadoptowany przez podstarzałego demona, któremu zamarzyło się posiadanie wnuka. A że świeżo upieczony dziadek bierze sobie swoją nową rolę bardzo do serca, w trosce o edukację chłopaka, posyła go do szkoły dla demonów. Nie to, że obecność człowieka w takiej placówce jest wskazana, czy nawet... bezpieczna. Chcąc nie chcąc Iruma musi wykorzystać wszystkie możliwe pomysły, nawet te najbardziej szalone, by ukryć swoje prawdziwe pochodzenie i nie zostać pożartym przez kolegów z klasy.";
        String irumaAuthor = "Nishi Osamu";

        String fireForceTitle = "Fire Force";
        String fireForceDescriptionEn = "Terror has paralyzed the clockwork metropolis of Tokyo! Possessed by demons, people have begun to burst into flame, leading to the establishment of a special firefighting department: the Fire Force, ready to roll on a moment's notice to fight spontaneous combustion anywhere it might break out. One of the Fire Force teams is about to get a unique addition: Shinra, a boy who possesses the unique power to run at the speed of a rocket, leaving behind the famous \"devil's footprints\" and destroying his shoes in the process. Can the Fire Force discover the source of this strange phenomenon and put a stop to it? Or will the city burn to ashes first?";
        String fireForceDescriptionPl = "W 198 roku Epoki Słońca w Tokio istnieje specjalna jednostka strażacka, która walczy z Infernalami, ludźmi, którzy zostali przemienieni w piekielne istoty. Infernale są istotami pierwszej generacji, późniejsze generacje posiadają zdolność do manipulowania płomieniami, zachowując jednocześnie ludzką formę. Młody Shinra, obdarzony umiejętnością zapalania stóp i podróżowania z prędkością rakiety, chce zostać bohaterem, więc wie, że 8. Specjalna Kompania Straży Pożarnej jest miejscem, gdzie będzie mógł to osiągnąć.";
        String fireForceAuthor = "Atsushi Ohkubo";

        String godOfHighschoolTitle = "The God of High School";
        String godOfHighschoolDescriptionEn = "It all began as a fighting tournament to seek out for the best fighter among all high school students in Korea. Mori Jin, a Taekwondo specialist and a high school student, soon learns that there is something much greater beneath the stage of the tournament.";
        String godOfHighschoolDescriptionPl = "Mori Jin trenuje sztuki walki przez całe swoje życie pod okiem przybranego \"dziadka\". Uczy się stylu koreańskich sztuk walki o nazwie Tae Kwon Do. Jednak jego styl w Tae Kwon Do jest nieco niekonwencjonalny. Decyduje się wziąć udział w konkursie o nazwie God of High School (GOH) po otrzymaniu osobistego zaproszenia. Konkurs ma na celu znalezienie najlepszego stylu i najlepszego zawodnika w sztuce walki. Największą nagrodą dla zwycięzcy ukończenia szkoły sztuk walki jest 1 milion dolarów i życzenie. ";
        String godOfHighschoolAuthor = "Park Yong-je";

        String narutoTitle = "Naruto";
        String narutoDescriptionEn = "Before Naruto's birth, a great demon fox had attacked the Hidden Leaf Village. A man known as the 4th Hokage sealed the demon inside the newly born Naruto, causing him to unknowingly grow up detested by his fellow villagers. Despite his lack of talent in many areas of ninjutsu, Naruto strives for only one goal: to gain the title of Hokage, the strongest ninja in his village. Desiring the respect he never received, Naruto works towards his dream with fellow friends Sasuke and Sakura and mentor Kakashi as they go through many trials and battles that come with being a ninja.";
        String narutoDescriptionPl = "Naruto to młody, hałaśliwy i nierozgarnięty ninja z Wioski Ukrytego Liścia. Jego największym marzeniem jest zostanie Hokage, jednak każdy w wiosce naśmiewa się z niego i mówi mu, że nie da rady. Naruto jednak nie poddaje się i stawia czoła wszystkim przeszkodom na swojej drodze, czym udowadnia innym, że się co do niego grubo mylili. Z czasem, ze słabego dzieciaka, którego każdy uważał za nic więcej jak potwora i nieudacznika, Naruto staje się silnym i odważnym shinobi, który ze wszystkich sił broni wioski i swoich przyjaciół, narażając przy tym własne życie.";
        String narutoAuthor = "Masashi Kishimoto";

        String blueExorcistTitle = "Blue Exorcist";
        String blueExorcistDescriptionEn = "Ao no Exorcist follows Rin Okumura who appears to be an ordinary, somewhat troublesome teenager—that is until one day he is ambushed by demons. His world turns upside down when he discovers that he is in fact the very son of Satan and that his demon father wishes for him to return so they can conquer Assiah together. Not wanting to join the king of Gehenna, Rin decides to begin training to become an exorcist so that he can fight to defend Assiah alongside his brother Yukio";
        String blueExorcistDescriptionPl = "Yukio i Rin Okumura prowadzą spokojne życie, ucząc się i dorastając pod okiem przybranego ojca, będącego równocześnie kapłanem. Rin to chłopak żywiołowy, wesoły i bardzo sprawny fizycznie, przy czym niezbyt rozgarnięty, zaś Yukio - jego zupełne przeciwieństwo. Chłopcy nie znają swoich prawdziwych rodziców, jednak nie stanowi to dla nich większego problemu - przynajmniej do dnia, w którym Rin dowiaduje się, że ich biologicznym ojcem jest sam władca Piekła, w anime zwanego Gehenną. Łatwo się domyślić, że ten fakt wprowadzi do życia bliźniaków ogromne zmiany i przysporzy nie lada kłopotów.";
        String blueExorcistAuthor = "Katou Kazue";

        String berserkTitle = "Berserk";
        String berserkDescriptionEn = "Gattsu, known as the Black Swordsman, seeks sanctuary from the demonic forces that persue himself and his woman, and also vengeance against the man who branded him as an unholy sacrifice. Aided only by his titanic strength, skill and sword, Gattsu must struggle against his bleak destiny, all the while fighting with a rage that might strip him of his humanity. Berserk is a dark and brooding story of outrageous swordplay and ominous fate, in the theme of Shakespeare's MacBeth.";
        String berserkDescriptionPl = "Guts, były najemnik znany obecnie jako \"Czarny Szermierz\", poszukuje zemsty. Pomimo traumatycznego dzieciństwa, z czasem udaje mu się odnaleźć kogoś wartego szacunku oraz, na pozór, godnego zaufania. Pewnego dnia fałszywy kompan odbiera Guts'owi wszystko co było dla niego ważne na rzecz swoich własnych, egoistycznych pragnień. Naznaczony symbolem ofiary, Guts zostaje skazany na wieczną ucieczkę przed nieustannie ścigającymi go demonicznymi stworami. Uzbrojony w gigantyczny miecz i potworną siłę wyrusza na usłane trudnościami poszukiwanie zemsty. Dopóki nie zabije tego kto odarł go - i jego ukochaną - z ich człowieczeństwa, nie pozwoli się powstrzymać nawet samej śmierci.";
        String berserkAuthor = "Kentarou Miura";

        String blackCloverTitle = "Black Clover";
        String blackCloverDescriptionEn = "Aster and Yuno were abandoned together at the same church, and have been inseparable since. As children, they promised that they would compete against each other to see who would become the next sorcerous emperor. However, as they grew up, some differences between them became plain. Yuno was a genius with magic, with amazing power and control, while Aster could not use magic at all, and tried to make up for his lack by training physically. When they received their grimoires at age 15, Yuno got a spectacular book with a four-leaf-clover (most people receive a three-leaf-clover), while Aster received nothing at all. However, when Yuno was threatened, the truth about Aster's power was revealed he received a five-leaf-clover grimoire, a 'black clover' book of anti-magic. Now the two friends are heading out in the world, both seeking the same goal!";
        String blackCloverDescriptionPl = "W świecie gdzie magia była wszystkim, urodził się chłopiec o imieniu Asta, niezdolny używać jakiejkolwiek magii. Z tego powodu został porzucony w biednym kościele. By udowodnić swoją wartość oraz dotrzymać obietnicę złożoną przyjacielowi chce zostać Magicznym Imperatorem.";
        String blackCloverAuthor = "Yūki Tabata";

        String kingdomTitle = "Kingdom";
        String kingdomDescriptionEn = "Millions of years have passed since the times of legends, when the worlds of man and gods were still the same. In these times it was the desires of man that moved the world. It is the era of the 500 year war - The Warring States period (403-222 BC in Chinese history). Kingdom is the story of a young boy named Shin who grew into a great general and all the trials and bloodshed that lead him there.";
        String kingdomDescriptionPl = "W Epoce Walczących Królestw w położonym na terenie Chin państwie Qin żyją sieroty – porywczy Shin i roztropny Hyou. Ich wspólnym marzeniem jest zdobycie tytułu „największego na świecie wodza”. Los daje Hyou możliwość pracy w pałacu, jednak chłopak po krótkim czasie powraca do wioski ciężko ranny i na łożu śmierci prosi Shina o wypełnienie pewnego obowiązku. Ma on odnaleźć Ei Seia i ochronić go przed spiskującym bratem. Chłopiec okazuje się łudząco podobnym do Hyou młodym cesarzem Qin…";
        String kingdomAuthor = "Yasuhisa Hara";

        String vinlandSagaTitle = "Vinland Saga";
        String vinlandSagaDescriptionEn = "As a child, Thorfinn sat at the feet of the great Leif Ericson and thrilled to wild tales of a land far to the west. But his youthful fantasies were shattered by a mercenary raid. Raised by the Vikings who murdered his family, Thorfinn became a terrifying warrior, forever seeking to kill the band’s leader, Askeladd, and avenge his father. Sustaining Thorfinn through his ordeal are his pride in his family and his dreams of a fertile westward land, a land without war or slavery… the land Leif called Vinland.";
        String vinlandSagaDescriptionPl = "Przez tysiąc lat wikingowie dorobili się dość dużej renomy jako jedni z najbardziej brutalnych i silnych wojowników. Thorfinn, syn jednego z najlepszych wikińskich wojów, postanawia poświęcić swe dziecięce lata na morderczy trening, który ma przybliżyć młodego wojownika do jego celu, którego nie może porzucić i którego pożąda najbardziej na świecie. Jego cel sprawia, że musi wyruszyć w podróż. Robi to wszystko, by odszukać pewną osobę i… pozbawić ją głowy za zamordowanie jego ojca.";
        String vinlandSagaAuthor = "Makoto Yukimura";

        String silentVoiceTitle = "A Silent Voice";
        String silentVoiceDescriptionEn = "Shouya Ishida, a boy always looking for ways to beat boredom, ends up looking for it in the wrong place. Weirded out by his new classmate, a deaf transfer student named Shouko Nishimiya, he deems her as the target of his ostracizing and bullying. Day after day, Shouya picks on Shouko, unaware of the effects of his thoughtless actions. He finally understands the pain he has inflicted on her when one day, his bullying culminates in her leaving the school, and his classmates begin to shun and harass him every chance they get instead. Determined to right his wrongs, five years later, Shouya, now a third year high school loner, meets Shouko again. Thus begins the story of a young man's path to redemption.";
        String silentVoiceDescriptionPl = "Shoya Ishida nade wszystko ceni przygody i dobrą zabawę. Jest energiczny i wszędzie go pełno. Kiedy więc w klasie pojawia się nowa koleżanka – niesłysząca Shoko Nishimiya – która już od pierwszych dni działa mu na nerwy nieprzystosowaniem i ślamazarnością, Shoya okazuje jej swoją niechęć. Nie tylko sam zaczyna uprzykrzać Nishimiyi życie, ale też zachęca do tego resztę klasy. Pewnego dnia posuwa się jednak za daleko, w efekcie czego dziewczyna zmienia szkołę, on zaś staje się obiektem przemocy i traci przyjaciół. Mija sześć lat. W tym czasie Shoya wielokrotnie zastanawiał się nad swoim karygodnym zachowaniem w stosunku do głuchej koleżanki. Czy będzie potrafił spojrzeć jej prosto w oczy, kiedy los ponownie skrzyżuje ich ścieżki?";
        String silentVoiceAuthor = "Yoshitoki Oima";

        saveManga(tokyoGhoulTitle, tokyoGhoulDescriptionEn, tokyoGhoulTitle, tokyoGhoulDescriptionPl,
                tokyoGhoulAuthor, action, supernatural, horror);
        saveManga(soloLevelingTitle, soloLevelingDescriptionEn, soloLevelingTitle, soloLevelingDescriptionPl,
                soloLevelingAuthor, action, adventure, fantasy);
        saveManga(beastarsTitle, beastarsDescriptionEn, beastarsTitle, beastarsDescriptionPl,
                beastarsAuthor, sliceOfLife, drama, psychological);
        saveManga(myHeroAcademiaTitle, myHeroAcademiaDescriptionEn, myHeroAcademiaTitle, myHeroAcademiaDescriptionPl,
                myHeroAcademiaAuthor, superPower, comedy, action);
        saveManga(komiTitle, komiDescriptionEn, komiTitle, komiDescriptionPl, komiAuthor, schoolLife, romance,
                comedy);

        if (!activeProfile.equals(TEST_PROFILE)) {

            saveManga(irumaTitle, irumaDescriptionEn, irumaTitle, irumaDescriptionPl, irumaAuthor, demons,
                    schoolLife, comedy);
            saveManga(fireForceTitle, fireForceDescriptionEn, fireForceTitle, fireForceDescriptionPl,
                    fireForceAuthor, action, supernatural);
            saveManga(godOfHighschoolTitle, godOfHighschoolDescriptionEn, godOfHighschoolTitle,
                    godOfHighschoolDescriptionPl, godOfHighschoolAuthor, fantasy, adventure, martialArts);
            saveManga(narutoTitle, narutoDescriptionEn, narutoTitle, narutoDescriptionPl, narutoAuthor, fantasy,
                    adventure, action);
            saveManga(blueExorcistTitle, blueExorcistDescriptionEn, blueExorcistTitle, blueExorcistDescriptionPl,
                    blueExorcistAuthor, demons, action, supernatural);
            saveManga(berserkTitle, berserkDescriptionEn, berserkTitle, berserkDescriptionPl, berserkAuthor,
                    mature, fantasy, adventure);
            saveManga(blackCloverTitle, blackCloverDescriptionEn, blackCloverTitle, blackCloverDescriptionPl,
                    blackCloverAuthor, magic, shounen, action);
            saveManga(kingdomTitle, kingdomDescriptionEn, kingdomTitle, kingdomDescriptionPl, kingdomAuthor,
                    historical, military, action);
            saveManga(vinlandSagaTitle, vinlandSagaDescriptionEn, vinlandSagaTitle, vinlandSagaDescriptionPl, vinlandSagaAuthor, historical,
                    drama, action);
            saveManga(silentVoiceTitle, silentVoiceDescriptionEn, silentVoiceTitle, silentVoiceDescriptionPl
                    , silentVoiceAuthor, sliceOfLife, drama);
        }
    }

    private void saveManga(String titleEn, String descriptionEn, String titlePl, String descriptionPl,
                           String authorFullName, MangaGenre... genres) {

        MangaTranslation mangaTranslationEn = MangaTranslation.builder().title(titleEn).description(descriptionEn)
                .language(en).build();
        MangaTranslation mangaTranslationPl = MangaTranslation.builder().title(titlePl).description(descriptionPl)
                .language(pl).build();

        byte[] image = null;

        try {
            image = imageToByteConverter.convertImageToByte(titleEn);
        } catch (IOException e) {
            log.info("Can`t  find image with name: " + titleEn);
        }

        Manga manga = Manga.builder().image(image).build();
        manga.addTranslation(mangaTranslationEn);
        manga.addTranslation(mangaTranslationPl);

        for (MangaGenre genre : genres) {
            manga.addGenre(genre);
        }

        Author author = new Author(authorFullName);
        manga.addAuthor(author);

        mangaRepo.save(manga);
    }
}
