import React from 'react';
import { StimulsoftViewer } from 'stimulsoft-viewer-react';

export const App: React.FC = () => {

    const properties = { reportName: "MasterDetail.mrt"};

    return (
        <StimulsoftViewer
            requestUrl="http://localhost:8080/webviewer/react_viewer?a={action}"
            action="InitViewer"
	    properties={properties}
            height="100vh"
        />
    );
};
