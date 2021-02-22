package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.utils.JPAUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    private UsuarioDao dao;

    private EntityManager em;

    @Before
    public void beforeEach() {
        em = JPAUtils.getEntityManager();
        dao = new UsuarioDao(em);
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
    public void deveriaExcluirUmUsuario() {
        Usuario usuario = criarUsuario();

        dao.deletar(usuario);

        Assert.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername(usuario.getNome()));
    }

    @Test
    public void deveriaAcharUsuarioPeloUsername() {
        Usuario usuario = criarUsuario();

        Usuario procurado = this.dao.buscarPorUsername(usuario.getNome());

        Assert.assertNotNull(procurado);

    }

    @Test
    public void naoDeveriaAcharUsuarioPeloUsername() {
        Usuario usuario = criarUsuario();

        Assert.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername(usuario.getNome() + " diferente"));

    }

}