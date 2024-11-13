package entidades;

import constantes.DefineStatus;
import constantes.FormatarData;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String codigoDeBarra;
    private String marca;
    private String status;
    private boolean situacao;
    private double quantidade;
    private double preco;
    private double total;
    private double custo;
    private double lucro;
    private double margemLucro;
    private Date dataDeCadastro;

    public Produto(Long id, String descricao, String codigoDeBarra, String marca, String status, boolean situacao, double quantidade, double preco, double total, double custo, double lucro, double margemLucro, Date dataDeCadastro) {
        this.id = id;
        this.descricao = descricao;
        this.codigoDeBarra = codigoDeBarra;
        this.marca = marca;
        this.status = status;
        this.situacao = situacao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
        this.custo = custo;
        this.lucro = lucro;
        this.margemLucro = margemLucro;
        this.dataDeCadastro = dataDeCadastro;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the codigoDeBarra
     */
    public String getCodigoDeBarra() {
        return codigoDeBarra;
    }

    /**
     * @param codigoDeBarra the codigoDeBarra to set
     */
    public void setCodigoDeBarra(String codigoDeBarra) {
        this.codigoDeBarra = codigoDeBarra;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
        calcularTotal();
        calcularTotal();
    }

    /**
     * @return the preco
     */
    public double getPreco() {
        return preco;
    }

    /**
     * @param preco the preco to set
     */
    public void setPreco(double preco) {
        this.preco = preco;
        calcularTotal();
        calcularTotal();
        calcularLucro();
        calcularLucro();
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the situacao
     */
    public boolean getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
        definirSituacao();
        definirSituacao();
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    public Produto() {
        this.dataDeCadastro = new Date();
    }

    /**
     * @return the dataDeCadastro
     */
    public Date getDataDeCadastro() {
        return dataDeCadastro;
    }

    /**
     * @param dataDeCadastro the dataDeCadastro to set
     */
    public void setDataDeCadastro(Date dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    /**
     * @return the custo
     */
    public double getCusto() {
        return custo;
    }

    /**
     * @param custo the custo to set
     */
    public void setCusto(double custo) {
        this.custo = custo;
        calculoMargemLucro();
        calculoMargemLucro();
        calcularLucro();
        calcularLucro();
    }

    /**
     * @return the margemLucro
     */
    public double getMargemLucro() {
        return margemLucro;
    }

    /**
     * @param margemLucro the margemLucro to set
     */
    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    /**
     * @return the lucro
     */
    public double getLucro() {
        return lucro;
    }

    /**
     * @param lucro the lucro to set
     */
    public void setLucro(double lucro) {
        this.lucro = lucro;
        calculoMargemLucro();
        calculoMargemLucro();
    }

    public void calcularTotal() {
        this.total = this.quantidade * this.preco;
    }

    public void definirSituacao() {
        if (this.getSituacao() == true) {
            this.setStatus(DefineStatus.STATUSINATIVO);
        } else {
            this.setStatus(DefineStatus.STATUSATIVO);
        }
    }

    public void calculoMargemLucro() {
        this.setMargemLucro((this.getLucro() / this.getCusto()) * 100);
    }

    public void calcularLucro() {
        this.setLucro(this.getPreco() - this.getCusto());
    }

    public void defineDataCadastro() {
        FormatarData formatarData = new FormatarData();
        SimpleDateFormat DataCadastro = formatarData.getFormatoData(getDataDeCadastro());
    }
}
