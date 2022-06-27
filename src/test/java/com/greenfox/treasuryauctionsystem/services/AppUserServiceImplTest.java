package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.AppUserNotFoundException;
import com.greenfox.treasuryauctionsystem.exceptions.IllegalArgumentException;
import com.greenfox.treasuryauctionsystem.exceptions.IllegalRegexException;
import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AppUserServiceImplTest {

    // POSITIVE
    @Test
    void saveAppUser() {

        AppUserRepository fakeAppUserRepository = Mockito.mock(AppUserRepository.class);

        AppUser fakeAppUser = new AppUser(
                1L,
                "user01",
                "lukacs.dvid@gmail.com",
                "Auction0123!?#",
                null,
                false,
                "Morgan Stanley",
                "a4cb8896-b3b9-4fd5-9dde-4827f8e09d68",
                null,
                false,
                "72bf5f79-f106-4b4c-af50-8a6dba130a75",
                null);

        Mockito.when(fakeAppUserRepository.save(Mockito.any())).thenReturn(fakeAppUser);

        AppUserService appUserService = new AppUserServiceImpl(fakeAppUserRepository);

        AppUser result = appUserService.saveAppUser(fakeAppUser);

        assertEquals(1L, result.getId());
        assertEquals("user01", result.getUsername());
        assertEquals("lukacs.dvid@gmail.com", result.getEmail());
        assertEquals("Auction0123!?#", result.getPassword());
        assertNull(result.getBids());
        assertFalse(result.isAdmin());
        assertEquals("Morgan Stanley", result.getInstitution());
        assertEquals("a4cb8896-b3b9-4fd5-9dde-4827f8e09d68", result.getActivationToken());
        assertNull(result.getActivationTokenExpiration());
        assertFalse(result.isActivated());
        assertEquals("72bf5f79-f106-4b4c-af50-8a6dba130a75", result.getReactivationToken());
        assertNull(result.getReactivationTokenExpiration());
    }

    // NEGATIVE
    @Test
    void saveAppUserFails() {

        AppUserRepository fakeAppUserRepository = Mockito.mock(AppUserRepository.class);

        // empty fields
        AppUser fakeAppUser = new AppUser(
                2L,
                "",
                "",
                "",
                null,
                false,
                "",
                "a4cb8896-b3b9-4fd5-9dde-4827f8e09d68",
                null,
                false,
                "72bf5f79-f106-4b4c-af50-8a6dba130a75",
                null);

        Mockito.when(fakeAppUserRepository.save(Mockito.any())).thenReturn(fakeAppUser);

        AppUserService appUserService = new AppUserServiceImpl(fakeAppUserRepository);

        assertThrows(IllegalArgumentException.class, () -> appUserService.saveAppUser(fakeAppUser));

        // regex fail
        fakeAppUser.setUsername("user01");
        fakeAppUser.setEmail("lukacs.dvid@gmail.com");
        fakeAppUser.setPassword("123");
        fakeAppUser.setInstitution("Morgan Stanley");

        assertThrows(IllegalRegexException.class, () -> appUserService.saveAppUser(fakeAppUser));
    }

    // POSITIVE
    @Test
    void activateAccount() {

        AppUserRepository fakeAppUserRepository = Mockito.mock(AppUserRepository.class);

        AppUser fakeAppUser = new AppUser(
                3L,
                "user01",
                "lukacs.dvid@gmail.com",
                "Auction0123!?#",
                null,
                false,
                "Morgan Stanley",
                "a4cb8896-b3b9-4fd5-9dde-4827f8e09d68",
                null,
                false,
                "72bf5f79-f106-4b4c-af50-8a6dba130a75",
                null);

        Mockito.when(fakeAppUserRepository.save(Mockito.any())).thenReturn(fakeAppUser);

        AppUserService appUserService = new AppUserServiceImpl(fakeAppUserRepository);

        AppUser result = appUserService.saveAppUser(fakeAppUser);

        // assert, value is false
        assertFalse(result.isActivated());

        // change it to true
        result.setActivated(true);

        // assert, value is true
        assertTrue(result.isActivated());
    }

    // NEGATIVE
    @Test
    void activateAccountFails() {

        AppUserRepository fakeAppUserRepository = Mockito.mock(AppUserRepository.class);

        AppUser fakeAppUser = new AppUser(
                4L,
                "user01",
                "lukacs.dvid@gmail.com",
                "Auction0123!?#",
                null,
                false,
                "Morgan Stanley",
                "a4cb8896-b3b9-4fd5-9dde-4827f8e09d68",
                null,
                false,
                "72bf5f79-f106-4b4c-af50-8a6dba130a75",
                null);

        Mockito.when(fakeAppUserRepository.save(Mockito.any())).thenReturn(fakeAppUser);

        AppUserService appUserService = new AppUserServiceImpl(fakeAppUserRepository);

        AppUser result = appUserService.saveAppUser(fakeAppUser);

        // passing in wrong token
        assertThrows(AppUserNotFoundException.class, () -> appUserService.activateAccount("a4cb8896-b3b9-4fd5-9dde-4827f8e09d689999999999999"));

        // TODO expired token
        // result.setActivationTokenExpiration();
    }

    // for DELETE
    // we have the user assert
    // delete it
    // we dont have it assert
}