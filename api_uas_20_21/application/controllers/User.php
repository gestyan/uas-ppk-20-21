<?php
defined('BASEPATH') or exit('No direct script access allowed');

class User extends CI_Controller
{

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('user_model');
	}

	// Method untuk login 
	public function login()
	{
		$response = [];
		if (true) {
			$username = $_POST['username'];
			$password = $_POST['password'];

			// $username = 'Gestyan';
			// $password = 'Ramadhan';

			$response = $this->user_model->login($username, $password);

			$json = json_encode($response, JSON_PRETTY_PRINT);
			echo $json;
		} else {
			echo "Bukan post";
		}
	}

	//Method untuk register
	public function register()
	{
		$response = $this->user_model->register();
		$json = json_encode($response, JSON_PRETTY_PRINT);

		echo $json;
	}

	// Method untuk update profil
	public function update()
	{
		$response = $this->user_model->update();
		$json = json_encode($response, JSON_PRETTY_PRINT);

		echo $json;
	}
}
