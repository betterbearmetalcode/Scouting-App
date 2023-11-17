

      // TODO(developer): Set to client ID and API key from the Developer Console
      const CLIENT_ID = '1041924237788-kiahub5cdmn74jo8j16o2j4eesnsn7eh.apps.googleusercontent.com';
      const API_KEY = 'AIzaSyDoQVkR6VrG-TRMHb2spzOar4py9Za7qTE';

      // Discovery doc URL for APIs used by the quickstart
      const DISCOVERY_DOC = 'https://sheets.googleapis.com/$discovery/rest?version=v4';

      // Authorization scopes required by the API; multiple scopes can be
      // included, separated by spaces.
      const SCOPES = 'https://www.googleapis.com/auth/spreadsheets';
      const spreadsheetId = "1ISNGUcObQgvC8pOLPRw2DZvNUy5lkMCjqjgicyDDmF8";
      let tokenClient;
      let gapiInited = false;
      let gisInited = false;

      let signedIn = false;

      /**
       * Callback after api.js is loaded.
       */
      function gapiLoaded() {
        gapi.load('client', initializeGapiClient);
      }

      /**
       * Callback after the API client is loaded. Loads the
       * discovery doc to initialize the API.
       */
      async function initializeGapiClient() {
        await gapi.client.init({
          apiKey: API_KEY,
          discoveryDocs: [DISCOVERY_DOC],
        });
        gapiInited = true;  
      }

      /**
       * Callback after Google Identity Services are loaded.
       */
      function gisLoaded() {
        tokenClient = google.accounts.oauth2.initTokenClient({
          client_id: CLIENT_ID,
          scope: SCOPES,
          callback: '', // defined later
        });
        gisInited = true;
      }


      /**
       *  Sign in the user upon button click.
       */
      function handleAuthClick() {
        tokenClient.callback = async (resp) => {
          if (resp.error !== undefined) {
            throw (resp);
          }
          signedIn = true;
          sendDataToSheets(arguments[0]);
          return true;
        };

        if (gapi.client.getToken() === null) {
          // Prompt the user to select a Google Account and ask for consent to share their data
          // when establishing a new session.
          tokenClient.requestAccessToken({prompt: 'consent'});
        } else {
          // Skip display of account chooser and consent dialog for an existing session.
          tokenClient.requestAccessToken({prompt: ''});
        }
        return signedIn;
      }

      /**
       *  Sign out the user upon button click.
       */
      function handleSignoutClick() {
        const token = gapi.client.getToken();
        if (token !== null) {
          google.accounts.oauth2.revoke(token.access_token);
          gapi.client.setToken('');
          signedIn = false;
        }
      }



      function parseData(content) {
        if (!signedIn) {
          handleAuthClick(content);
        }else {
          sendDataToSheets(content);
        }
        
      }


      async function sendDataToSheets(content) {
        let response;
        try {
          // Fetch first 10 files
          response = await gapi.client.sheets.spreadsheets.values.get({
            spreadsheetId: spreadsheetId,
            range: 'Controls!B2:B4',
          });
        } catch (err) {
          alert("Error getting name of control sheeet");
        }
        const eventKey = response.result.values[0];
        const targetSheetName = response.result.values[1];
        const tbaAPIKey = response.result.values[2];
        let values = [content.slice(3, content.length)];
        let row = 2 + ((parseInt(content[1]) - 1) * 6 + parseInt(content[0]));
        const body = {
          values: values,
        };

        try {
          gapi.client.sheets.spreadsheets.values.update({
            spreadsheetId: spreadsheetId,
            range: 'GirlsGen!N' + row + ':AL' + row,
            valueInputOption: 'RAW',
            resource: body,
          }).then((response) => {
            const result = response.result;
            console.log(`${result.updatedCells} cells updated.`);
            //if (callback) callback(response);
          });
        } catch (err) {
          alert("error posting data");
        }
      }