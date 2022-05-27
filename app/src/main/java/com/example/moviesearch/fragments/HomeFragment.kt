package com.example.moviesearch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.*
import com.example.moviesearch.animation.AnimationHelper
import com.example.moviesearch.recyclerView.DiffUtil
import com.example.moviesearch.recyclerView.Film
import com.example.moviesearch.recyclerView.FilmListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {
    private var filmsDataBase = listOf(
        Film(
            "Интерстеллар",
            R.drawable.poster_recyclerview_interstellar,
            "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
        ),
        Film(
            "Тайна Коко",
            R.drawable.poster_recyclerview_tainy_coco,
            "12-летний Мигель живёт в мексиканской деревушке в семье сапожников и тайно мечтает стать музыкантом. Тайно, потому что в его семье музыка считается проклятием. Когда-то его прапрадед оставил жену, прапрабабку Мигеля, ради мечты, которая теперь не даёт спокойно жить и его праправнуку. С тех пор музыкальная тема в семье стала табу. Мигель обнаруживает, что между ним и его любимым певцом Эрнесто де ла Крусом, ныне покойным, существует некая связь. Паренёк отправляется к своему кумиру в Страну Мёртвых, где встречает души предков. Мигель знакомится там с духом-скелетом по имени Гектор, который становится его проводником. Вдвоём они отправляются на поиски де ла Круса."
        ),
        Film(
            "Назад в будущее",
            R.drawable.poster_recyclerview_back_in_the_future,
            "Подросток Марти с помощью машины времени, сооружённой его другом-профессором доком Брауном, попадает из 80-х в далекие 50-е. Там он встречается со своими будущими родителями, ещё подростками, и другом-профессором, совсем молодым."
        ),
        Film(
            "Валл-и",
            R.drawable.poster_recyclerview_valli,
            "Робот ВАЛЛ·И из года в год прилежно трудится на опустевшей Земле, очищая нашу планету от гор мусора, которые оставили после себя улетевшие в космос люди. Он и не представляет, что совсем скоро произойдут невероятные события, благодаря которым он встретит друзей, поднимется к звездам и даже сумеет изменить к лучшему своих бывших хозяев, совсем позабывших родную Землю."
        ),
        Film(
            "Начало",
            R.drawable.poster_recyclerview_nachalo,
            "Кобб – талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил. И вот у Кобба появляется шанс исправить ошибки. Его последнее дело может вернуть все назад, но для этого ему нужно совершить невозможное – инициацию. Вместо идеальной кражи Кобб и его команда спецов должны будут провернуть обратное. Теперь их задача – не украсть идею, а внедрить ее. Если у них получится, это и станет идеальным преступлением. Но никакое планирование или мастерство не могут подготовить команду к встрече с опасным противником, который, кажется, предугадывает каждый их ход. Врагом, увидеть которого мог бы лишь Кобб."
        ),
        Film(
            "Темный рыцарь",
            R.drawable.poster_recyclerview_temniy_rycar,
            "Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить улицы Готэма от преступности. Сотрудничество оказывается эффективным, но скоро они обнаружат себя посреди хаоса, развязанного восходящим криминальным гением, известным напуганным горожанам под именем Джокер."
        ),
        Film(
            "Бойцовский клуб",
            R.drawable.poster_recyclerview_boicovskii_club,
            "Сотрудник страховой компании страдает хронической бессонницей и отчаянно пытается вырваться из мучительно скучной жизни. Однажды в очередной командировке он встречает некоего Тайлера Дёрдена — харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а единственное, ради чего стоит жить — саморазрушение. Проходит немного времени, и вот уже новые друзья лупят друг друга почем зря на стоянке перед баром, и очищающий мордобой доставляет им высшее блаженство. Приобщая других мужчин к простым радостям физической жестокости, они основывают тайный Бойцовский клуб, который начинает пользоваться невероятной популярностью."
        ),
        Film(
            "Карты, деньги, два ствола",
            R.drawable.poster_recyclerview_karty_dengi_dva_stvola,
            "Четверо молодых парней накопили каждый по 25 тысяч фунтов, чтобы один из них мог сыграть в карты с опытным шулером и матерым преступником, известным по кличке Гарри-Топор. Парень в итоге проиграл 500 тысяч, на уплату долга ему дали неделю. В противном случае и ему и его «спонсорам» каждый день будут отрубать по пальцу, а потом... Чтобы выйти из положения, ребята решили ограбить бандитов, решивших ограбить трех «ботаников», выращивающих марихуану для местного наркобарона. Но на этом приключения четверки не заканчиваются..."
        ),
        Film(
            "Унесенные призраками",
            R.drawable.poster_recyclerview_unesennie_prizrakami,
            "Тихиро с мамой и папой переезжает в новый дом. Заблудившись по дороге, они оказываются в странном пустынном городе, где их ждет великолепный пир. Родители с жадностью набрасываются на еду и к ужасу девочки превращаются в свиней, став пленниками злой колдуньи Юбабы. Теперь, оказавшись одна среди волшебных существ и загадочных видений, Тихиро должна придумать, как избавить своих родителей от чар коварной старухи."
        )
    )
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private var newList = listOf<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView(view)
        initRecyclerView()
        filmsAdapter.addItems(filmsDataBase)


        AnimationHelper.performFragmentCircularRevealAnimation(fragment_home_root, requireActivity(), 4)

    }

    private fun searchView(view: View) {
        val search =
            view.findViewById<androidx.appcompat.widget.SearchView>(R.id.fragment_home_search)
        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (newText.isNullOrBlank()) {
                    filmsAdapter.addItems(filmsDataBase)
                    true
                } else {
                    val result = filmsDataBase.filter {
                        it.title.lowercase(
                            Locale.getDefault()
                        ).contains(newText.lowercase(Locale.getDefault()))
                    }
                    filmsAdapter.addItems(result)
                    true
                }
            }
        })
    }


    private fun initRecyclerView() {
        recycler_view.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

        }
        filmsAdapter.addItems(filmsDataBase)

        val diff = DiffUtil(filmsDataBase, newList)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diff)
        newList = filmsDataBase
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }
}