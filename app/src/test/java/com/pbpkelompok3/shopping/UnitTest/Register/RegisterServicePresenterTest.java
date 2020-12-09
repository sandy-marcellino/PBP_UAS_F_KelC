package com.pbpkelompok3.shopping.UnitTest.Register;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServicePresenterTest {

    @Mock
    private RegisterView view;
    @Mock
    private RegisterService service;
    private RegisterServicePresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegisterServicePresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenFirstNameEmpty() throws Exception{
        when(view.getFirst()).thenReturn("");
        System.out.println("First Name : "+view.getFirst());

        presenter.onRegisterClicked();

        verify(view).showFirstError("First name is required");
    }

    @Test
    public void shouldShowErrorMessageWhenLastNameEmpty() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("");
        System.out.println("Last Name : "+view.getLast());

        presenter.onRegisterClicked();

        verify(view).showLastError("Last name is required");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailEmpty() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("");
        System.out.println("Email : "+view.getEm());

        presenter.onRegisterClicked();

        verify(view).showEmailError("Email is required");
    }

    @Test
    public void shouldShowErrorMessageWhenPassEmpty() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("dewasamudra17@gmail.com");
        System.out.println("Email : "+view.getEm());

        when(view.getPass()).thenReturn("");
        System.out.println("Password : "+view.getPass());

        presenter.onRegisterClicked();

        verify(view).showPassError("Password is required");
    }

    @Test
    public void shouldShowErrorMessageWhenNoTelpEmpty() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("dewasamudra17@gmail.com");
        System.out.println("Email : "+view.getEm());

        when(view.getPass()).thenReturn("kertas123");
        System.out.println("Password : "+view.getPass());

        when(view.getTelp()).thenReturn("");
        System.out.println("Phone Number : "+view.getTelp());

        presenter.onRegisterClicked();

        verify(view).showTelpError("Phone Number is required");
    }

    @Test
    public void shouldShowErrorMessageWhenPassInvalid() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("dewasamudra17@gmail.com");
        System.out.println("Email : "+view.getEm());

        when(view.getPass()).thenReturn("kert");
        System.out.println("Password : "+view.getPass());

        when(view.getTelp()).thenReturn("0821593595333");
        System.out.println("Phone Number : "+view.getTelp());

        when(service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp())).thenReturn(false);

        System.out.println("Hasil : "+service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp()));
        presenter.onRegisterClicked();
    }

    @Test
    public void shouldShowErrorMessageWhenNoTelpInvalid() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("dewasamudra17@gmail.com");
        System.out.println("Email : "+view.getEm());

        when(view.getPass()).thenReturn("kertas123");
        System.out.println("Password : "+view.getPass());

        when(view.getTelp()).thenReturn("0821456");
        System.out.println("Phone Number : "+view.getTelp());

        when(service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp())).thenReturn(false);

        System.out.println("Hasil : "+service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp()));
        presenter.onRegisterClicked();
    }

    @Test
    public void shouldShowErrorMessageWhenEmailInvalid() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("dewasamudra17");
        System.out.println("Email : "+view.getEm());

        when(view.getPass()).thenReturn("kertas123");
        System.out.println("Password : "+view.getPass());

        when(view.getTelp()).thenReturn("0821593595333");
        System.out.println("Phone Number : "+view.getTelp());

        when(service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp())).thenReturn(false);

        System.out.println("Hasil : "+service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp()));
        presenter.onRegisterClicked();
    }

    @Test
    public void shouldStartLoginActivity() throws Exception{
        when(view.getFirst()).thenReturn("lawrenxius");
        System.out.println("First Name : "+view.getFirst());

        when(view.getLast()).thenReturn("benny");
        System.out.println("Last Name : "+view.getLast());

        when(view.getEm()).thenReturn("dewasamudra17@gmail.com");
        System.out.println("Email : "+view.getEm());

        when(view.getPass()).thenReturn("kertas123");
        System.out.println("Password : "+view.getPass());

        when(view.getTelp()).thenReturn("0821593595333");
        System.out.println("Phone Number : "+view.getTelp());

        when(service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp())).thenReturn(true);

        System.out.println("Hasil : "+service.getValid(view,view.getFirst(),view.getLast(),view.getEm(),view.getPass(), view.getTelp()));
        presenter.onRegisterClicked();
    }
}