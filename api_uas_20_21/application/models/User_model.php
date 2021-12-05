<?php
class User_model extends CI_Model
{
    public function __construct()
    {
        $this->load->database();
    }

    public function login($username, $password)
    {
        $this->db->where('username', $username);
        $query = $this->db->get('user')->row();

        $response = [];

        if ($query == null) { //Kalo username ga terdaftar
            $response['status'] = "false";
            $response['message'] = "username tidak terdaftar";
        } else {
            if (strcmp(md5($password), $query->password) == 0) { //passwordnya cocok enggak
                $response['status'] = "true";
                $response['message'] = "berhasil login";
                $response['data'] = [
                    'id'        => $query->id,
                    'username'  => $query->username,
                    'fullname'  => $query->fullname,
                    'email'     => $query->email
                ];
            } else {
                $response['status'] = "false";
                $response['message'] = "password tidak sesuai";
            }
        }

        return $response;
    }

    public function register()
    {
        $username = $this->input->post('username');

        $this->db->where('username', $username);
        $query = $this->db->get('user')->row();

        $response = [];
        $response['status'] = "false";

        if ($query != null) { //Ngecek username udah terdaftar apa belom
            $response['message'] = "username telah terdaftar";
        } else {
            $password_regx = "/^(?=.*[a-z])(?=.*[0-9])/";

            $fullname = $this->input->post('fullname');
            $email = $this->input->post('email');
            $password = $this->input->post('password');
            $confirm_password = $this->input->post('confirm_password');

            $response['message'] = [
                'username' => (strlen($username) < 4) ? 'username minimal 4 huruf' : 'true',
                'fullname' => (strlen($fullname) - 1  < 0) ? 'nama harus diisi' : 'true',
                'email'     => (!filter_var($email, FILTER_VALIDATE_EMAIL)) ? 'email tidak valid' : 'true',
                'password'    => (!preg_match($password_regx, $password)) ? 'password harus terdiri dari angka dan huruf' : 'true',
                'confirm_password' => (strcmp($password, $confirm_password) != 0 || !isset($confirm_password)) ? 'konfirmasi password tidak sesuai' : 'true',
            ];
        }

        $mustTrue = count($response['message']);
        $isTrue = 0;

        foreach ($response['message'] as $key => $value) {
            if ($value == 'true')
                $isTrue++;
        }

        if ($isTrue == $mustTrue) {

            $this->db->set('username', $username);
            $this->db->set('email', $email);
            $this->db->set('fullname', $fullname);
            $this->db->set('password', md5($password));

            $this->db->insert('user');

            if ($this->db->affected_rows() == 1) {
                $response['status'] = "true";
                $response['message'] = [
                    'status' => "berhasil melakukan registrasi"
                ];
            } else {
                $response['status'] = "false";
                $response['message'] = [
                    'status' => "terjadi kesalahan, gagal melakukan registrasi"
                ];
            }
        }
        return $response;
    }

    public function update()
    {

        $id = $this->input->post('id');
        $fullname = $this->input->post('fullname');
        $email = $this->input->post('email');
        $password = $this->input->post('password');

        // Cek usernya ada atau enggak
        $this->db->where('id', $id);
        $query = $this->db->get('user')->row();

        if ($this->db->affected_rows() == 0) {
            $response['status'] = "false";
            $response['message'] = "id tidak ditemukan";

            $response['data'] = [
                'id'        => null,
                'username'  => null,
                'fullname'  => null,
                'email'     => null
            ];

            return  $response;
        }

        // Set data yang mau diupdate
        $this->db->set('fullname', $fullname);
        $this->db->set('email', $email);
        if (isset($password)) {
            $this->db->set('password', md5($password));
        }
        $this->db->where('id', $id);
        $this->db->update('user');

        // Ngambil data terbaru
        $this->db->where('id', $id);
        $query = $this->db->get('user')->row();

        $response = [];

        if ($query != null) {
            $response['status'] = "true";
            $response['message'] = "sukses update data";
            $response['data'] = [
                'id'        => $query->id,
                'username'  => $query->username,
                'fullname'  => $query->fullname,
                'email'     => $query->email
            ];
        } else {
            $response['status'] = "false";
            $response['message'] = "gagal dapat data baru";
            $response['data'] = [
                'id'        => null,
                'username'  => null,
                'fullname'  => null,
                'email'     => null
            ];
        }
        return $response;
    }
}
