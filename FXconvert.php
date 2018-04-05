<?php
        
    $page = file_get_contents('https://www.jncb.com/Support/Help-Resources/Foreign-Exchange-Services');
   
    $dom = new DOMDocument();
    $html = $page;
    $data = array();
    $fx = array();
    $fxa = array();
    $fxC = array();
    $fxM = array();
    $fxRates = array();
    $cnt = 0;
    $n = 0;
    
    libxml_use_internal_errors(true);
    $dom->loadHTML($html);
    $xpath = new DOMXPath($dom);

    $my_xpath_query = "//table//tbody[contains(@class, 'FxRContainer')]//td";
    $result_rows = $xpath->query($my_xpath_query);


    foreach ($result_rows as $key=>$result_object) {  
        $data[] = $result_object->nodeValue;
    } 


    for ($i=0; $i < 28; ++$i) { 
        if( $i % 2 == 0 ) {
            $fx[] = $data[$i];
        }
    }

    //Trim extra Characters from infront currency/
    for ($i=0; $i < 14; ++$i) { 
        if ( $i % 2 == 0 ) {
            $fx[$i] = substr($fx[$i], 6);
        }
    }

    //Separate currency labels and currency
    for ($i=0; $i < 14; ++$i) {         
        if ($i % 2 == 0 ) {            
            $fxC[$n] = $fx[$i];
            $n++;
        }
    }
    for ($i=0; $i < 14; ++$i) { 
        if ($i % 2 <> 0) {
            $fxM[$cnt] = $fx[$i];
            $cnt++;
        }
    }

    //Combin 'fxC' and 'fxM' to on Associative Array
    $fxRates = array_combine($fxC, $fxM);
    
    echo json_encode($fxRates, JSON_PRETTY_PRINT);

?>
