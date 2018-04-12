<?php
        
    $page = file_get_contents('https://www.jncb.com/Support/Help-Resources/Foreign-Exchange-Services');
   
    $dom = new DOMDocument();
    $html = $page;
    $data = array();
    $fxA = array();
    $fxC = array();
    $fxRates = array("fxRates");
    
    libxml_use_internal_errors(true);
    $dom->loadHTML($html);
    $xpath = new DOMXPath($dom);

    $my_xpath_query = "//table//tbody[contains(@class, 'FxRContainer')]//td[position() = 1 or position() = 2]";
    $result_rows = $xpath->query($my_xpath_query);

    //Gets raw data from table
    foreach ($result_rows as $key=>$result_object) {  
        $data[] = trim($result_object->nodeValue);
    } 

    //Trim extra Characters from infront currency and separate Currency from Amount
    for ($i=0; $i < 14; ++$i) { 
        if ( $i % 2 == 0 ) {
            $fxC[]["Currency"] = substr($data[$i], 6);
        }
    }

    //Separate currency labels and currency
    for ($i=0; $i < 14; ++$i) {         
        if ($i % 2 != 0 ) {            
            $fxA[]["Amount"] = $data[$i];
        }
    }

    //Combine Arrays
    foreach ($fxC as $key => $value) {
        $fxRates[$key] = array_merge($fxC[$key], $fxA[$key]);
    }
    
    echo json_encode($fxRates, JSON_PRETTY_PRINT);

?>
