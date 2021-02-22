package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.utils.JPAUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoDaoTest {

    private LeilaoDao dao;

    private EntityManager em;

    @Before
    public void beforeEach() {
        em = JPAUtils.getEntityManager();
        dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @After
    public void afterEach() {
        em.getTransaction().rollback();
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123456");

        em.persist(usuario);

        return usuario;
    }

    @Test
    public void deveriaCadastrarUmLeilao() {
        Usuario usuario = criarUsuario();

        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());

        Assert.assertNotNull(salvo);
    }

    @Test
    public void deveriaAtualizarUmLeilao() {
        Usuario usuario = criarUsuario();

        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("4000"));

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());

        Assert.assertNotNull(salvo);
        Assert.assertEquals("Celular", salvo.getNome());
        Assert.assertEquals(new BigDecimal("4000"), salvo.getValorInicial());
    }



}