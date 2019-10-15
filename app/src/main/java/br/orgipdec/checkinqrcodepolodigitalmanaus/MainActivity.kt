package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.orgipdec.checkinqrcodepolodigitalmanaus.api.ApiServiceInterface
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnAPIIPDEC
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val subscriptions = CompositeDisposable()
    private val ZXING_CAMERA_PERMISSION = 1
    private var mClss: Class<*>? = null
    private val api: ApiServiceInterface = ApiServiceInterface.create()

    private var listaDia : ArrayList<String> =  ArrayList<String>()

    private var listaSala : ArrayList<String> =  ArrayList<String>()

    private var listaPalestra : ArrayList<String> =  ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (!SharedPreferences.checkInfo(this)) {  // Se for o primeiro Login

            SharedPreferences.setInfo(this, true)

            setContentView(R.layout.activity_main)

            pbCarregarTela.setVisibility(View.VISIBLE) // VISIBLE PROGRESSBAR
            carregarDia()

            spnDia!!.setOnItemSelectedListener(this)
            spnSala!!.setOnItemSelectedListener(this)

            btnCredenciar.setOnClickListener{

                pbCarregarTela.setVisibility(View.VISIBLE)
                // Setando Variáveis Globais
                SharedPreferences.setOrganizador(this, edtOrganizador.text.toString())
                SharedPreferences.setDia(this, listaDia.get(spnDia.getSelectedItemPosition()))
                SharedPreferences.setSala(this, listaSala.get(spnSala.getSelectedItemPosition()))
                SharedPreferences.setPalestra(this, listaPalestra.get(spnPalestra.getSelectedItemPosition()))

                launchActivity()
            }
        }
        else{ // SE NAO FOR O PRIMEIRO LOGIN

            val intent = Intent(this, HostQRCode::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

        when(arg0!!.id) {
            R.id.spnDia ->{
                pbCarregarTela.setVisibility(View.VISIBLE) // VISIBLE PROGRESSBAR
                carregarSala(listaDia.get(position))
            }
            R.id.spnSala -> {
                pbCarregarTela.setVisibility(View.VISIBLE) // VISIBLE PROGRESSBAR
                carregarPalestra(listaDia.get(spnDia.getSelectedItemPosition()), listaSala.get(position))
            }
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun launchActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pbCarregarTela.setVisibility(View.INVISIBLE)
        //   Caso Nao tenha aceito em nenhum momento o uso da camera
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION)


        } else {
            // Caso ja aceito o uso da camera
            pbCarregarTela.setVisibility(View.INVISIBLE)
            val intent = Intent(this, HostQRCode::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ZXING_CAMERA_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Após Aceitar Permissao
                    launchActivity()

                } else {
                    // Após Negar Permissao
                }
                return
            }
        }
    }
    fun carregarDia() {

        listaDia  =  ArrayList<String>()
        var subscribe = api.getJSONAPI().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ReturnAPIIPDEC? ->

                list!!.days.forEach { // Dias
                    listaDia!!.add("Dia ${it.id}")
                    }

                // Adapter do Dia !
                val aAdapterDia = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDia!!.toList())
                aAdapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnDia!!.setAdapter(aAdapterDia)
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR


            }, { error ->
                Log.i("Resultadojfs", "Erro: : ${error.localizedMessage}")
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            })

        subscriptions.add(subscribe)
    }
    fun carregarSala(dia : String) {

        listaSala  =  ArrayList<String>()
        var subscribe = api.getJSONAPI().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ReturnAPIIPDEC? ->

                list!!.days.forEach {
                    if(("Dia ${it.id}").equals(dia)) {
                        it.trilha.forEach {
                            // Salas
                            listaSala!!.add("${it.room} - ${it.title}")
                        }
                    }
                  }
                // Adapter da Sala !
                val aAdapteSala = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSala!!.toList())
                aAdapteSala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnSala!!.setAdapter(aAdapteSala)
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            }, { error ->
                Log.i("Resultadojfs", "Erro: + ${error.message}")
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            })

        subscriptions.add(subscribe)
    }
    fun carregarPalestra(sala : String, trilha : String) {

        listaPalestra  =  ArrayList<String>()
        var subscribe = api.getJSONAPI().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ReturnAPIIPDEC? ->

                list!!.days.forEach {
                    if(("Dia ${it.id}").equals(sala)) {
                        it.trilha.forEach {
                            if ("${it.room} - ${it.title}".equals(trilha)) {
                                it.talks.forEach {
                                    listaPalestra!!.add(it.title)
                                    SharedPreferences.setIDPalestra(this, "${it.talkId}")
                                }
                            }
                        }
                    }
                }
                // Adapter da Palestra !
                val aAdaptePalestra = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaPalestra!!.toList())
                aAdaptePalestra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnPalestra!!.setAdapter(aAdaptePalestra)
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR

            }, { error ->
                Log.i("Resultadojfs", "Erro: + ${error.message}")
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            })

        subscriptions.add(subscribe)
    }

   /* fun carregarTudoLista() {


        if(spnDia.getSelectedItemPosition() == 1){ // 15

        }
        listaDia.add("15")

        listaSala.add("Salão Nobre - Cerimônia de Abertura")
        listaPalestra.add("Posicionamento de Autoridades")
        listaPalestra.add("Hino Nacional")
        listaPalestra.add("Apresentação do Vídeo - Manaus Inteligente")
        listaPalestra.add("Discursos e apresentações das autoridades")

        listaSala.add("Salão Nobre - Liderança & Criatividade & Empreendedorismo")
        listaPalestra.add("Painel Startups de Manaus")
        listaPalestra.add("Ecossistema Santa Catarina")
        listaPalestra.add("Cidades Digitais e inteligentes: Da Inteligência computacional para as Ciências Socias aplicadas")
        listaPalestra.add("Promovendo governança digital no estado Báltico")
        listaPalestra.add("Inovação digital + Transformação estratégica = transformação digital")

        listaSala.add("Sala 2 + 3 - Tecnologias e Sociedade")
        listaPalestra.add("Como prestar serviços através de assistentes de voz: caso de uso da plataforma Declare Fácil")
        listaPalestra.add("omputação ubíqua e móvel")
        listaPalestra.add("Das cidades inteligentes para cidades sensíveis: um case de uma jornada guiada em processos criativos")
        listaPalestra.add("Sistema de chamada de emergência e app \"aviso polícia\"")
        listaPalestra.add("Navegando por cidades inteligentes")

        listaSala.add("Auditório - Agilidade")
        listaPalestra.add("Ferramentas de Gestão 3.0")
        listaPalestra.add("Lean Inception")
        listaPalestra.add("Facilitação")
        listaPalestra.add("Product Backlog Building")

        listaSala.add("Sala 1 - Transformação Digital")
        listaPalestra.add("Painel Os desafios da transformação digital no Mundo VUCA")
        listaPalestra.add("Parque Mosaico, o primeiro bairro inteligente de Manaus")
        listaPalestra.add("Do analógico ao digital")
        listaPalestra.add("Instrumentos de apoio à transformação digital")
        listaPalestra.add("Escalabilidade de negócios digitais: novos KPIs para gestão em investimentos em marketing")




        listaDia.add("16")

        listaSala.add("Salão Nobre - Liderança & Criatividade & Empreendedorismo")
        listaPalestra.add("City Smartup - A nova era de cidades mais inteligentes")
        listaPalestra.add("Painel Empreendedorismo e Criatividade: como os negócios contribuem para o desenvolvimento de uma cidade")
        listaPalestra.add("Painel Cidades Criativas, Inteligentes, Humanas e Sustentáveis")
        listaPalestra.add("Criatividade para Solução de Problemas")
        listaPalestra.add("Ambientes de Empreendedorismo e Inovação: lançamento do edital para incubação de empresas")

        listaSala.add("Sala 2 + 3 - Tecnologias e Sociedade")
        listaPalestra.add("A Quarta Onda - E eu com isto?")
        listaPalestra.add("Caso 3M Manaus")
        listaPalestra.add("Indústria de Soluções para Manufatura Avançada")
        listaPalestra.add("Caso Technicolor")
        listaPalestra.add("A Indústria 4.0, o propósito, os processos e a liderança lean")
        listaPalestra.add("Cases de manufatura 4.0 utilizando financiamento EMBRAPII")

        listaSala.add("Auditório - Pesquisa Cientifica")
        listaPalestra.add("Deep Learning para Processamento de Linguagem Natural")
        listaPalestra.add("Ecossistema de Inovação da Alta Paulista")
        listaPalestra.add("O Programa Ciência na Escola")
        listaPalestra.add("Internet do Futuro e Cidades Inteligentes")
        listaPalestra.add("Aprendizado de Máquina e a Concepção de Funcionalidades para Sistemas de Comunicações Móveis de Próxima Geração")
        listaPalestra.add("Desenvolvendo novos produtos a partir da reciclagem e redução do impacto ambiental dos resíduos")

        listaSala.add("Sala 1 - Tecnologias Emergentes")
        listaPalestra.add("Tecnologias Emergentes")
        listaPalestra.add("Tendências Tecnológicas para 2020, ao infinito e além")
        listaPalestra.add("Transformação Digital")
        listaPalestra.add("Tecnologias Emergentes e a LGPD")
        listaPalestra.add("Análise de Maturidade e Prontidão da Indústria 4.0 no PIM")
        listaPalestra.add("Implementação da Indústria 4.0")




        listaDia.add("17")

        listaSala.add("Salão Nobre - Cerimônia de Encerramento")
        listaPalestra.add("Posicionamento de autoridades")
        listaPalestra.add("Discursos e apresentações das autoridades presentes")
        listaPalestra.add("Apresentação do vídeo do evento")

        listaSala.add("Salão Nobre - Jogos Digitais & Criatividade")
        listaPalestra.add("Case Behold Studios: como empreender em uma indústria em constante mudança")
        listaPalestra.add("Aprendizados com jogos AAA: Melhores práticas de Game Design")
        listaPalestra.add("Liderando Equipes para o Sucesso")
        listaPalestra.add("Desenvolva-se, Desenvolva sua Equipe, Desenvolva seu Jogo: Produzindo com Confiança")
        listaPalestra.add("A Linguagem da Emoção")
        listaPalestra.add("Inovação: a criatividade na era digital")

        listaSala.add("Sala 2 + 3 - UX/Design")
        listaPalestra.add("Um olhar do design estratégico para ecossistemas criativos e processos urbanos")
        listaPalestra.add("YouTube: a fotografia como ferramenta para guiar o desenvolvimento de produtos focados no usuário")
        listaPalestra.add("Modelos de Cidade para um Futuro Sustentável e Digital")
        listaPalestra.add("Expectativa, Experiência e o Elo Perdido")
        listaPalestra.add("Princípios básicos de UX e um quê de psicologia cognitiva")
        listaPalestra.add("Fazendo Design para as Linguagens do Futuro")

        listaSala.add("Auditório - e-Gov")
        listaPalestra.add("Transformação Digital na Esfera do Governo")
        listaPalestra.add("Parcerias entre o ecossistema de startups e o setor público para a transformação digital no Brasil")
        listaPalestra.add("Cidades Digitais")
        listaPalestra.add("Projeto Manaus Inteligente e Ambiente de Geocolaboração Prefeitura de Manaus / Gestão_Pública_4.0: Projeto Licenciamento Integrado municipal Prefeitura de Manaus")
        listaPalestra.add("Pitch Govtech")
        listaPalestra.add("Programa Prioritário de Economia Digital (INDT) e Programa Prioritário de Formação de RH (Fundação Muraki)")

        listaSala.add("Sala 1 - Tecnologia Verde / Sustentabilidade")
        listaPalestra.add("O uso da tecnologia para cidades mais biofílicas")
        listaPalestra.add("A Biotecnologia 4.0 e o Futuro do Desenvolvimento Sustentável da Amazônia")
        listaPalestra.add("Lixo eletrônico: desafios e oportunidades")
        listaPalestra.add("Microsoft Azure: Utilizando Inteligência Artificial para construir uma Nuvem Sustentável e Eficiente")
        listaPalestra.add("Programa Prioritário de Bioeconomia: Oportunidades da Bioeconomia para Diversificação da Matriz Econômica da Amazônia")
        listaPalestra.add("Usando dados para avaliação de políticas públicas de mobilidade urbana")

    } */
}
