<?php
$content = file_get_contents("ex7.txt");
$content = str_replace(array("\r", "\n"), "", $content);
$bytes = $content;
$key = "YELLOW SUBMARINE";
$decrypted = openssl_decrypt($bytes, "AES-128-ECB", $key);
echo $decrypted;
